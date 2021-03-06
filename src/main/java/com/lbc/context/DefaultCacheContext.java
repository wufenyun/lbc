/**
 *  * Package: com.lbc.config
 *  * Description: 
 *  
 */
package com.lbc.context;

import com.lbc.Cache;
import com.lbc.LocalCache;
import com.lbc.config.LbcConfiguration;
import com.lbc.context.event.CloseEvent;
import com.lbc.context.event.EventListener;
import com.lbc.context.event.EventMulticaster;
import com.lbc.context.event.SimpleEventMulticaster;
import com.lbc.CacheLoader;
import com.lbc.consistency.ConsistencyMonitor;
import com.lbc.consistency.eventdriven.ZkCacheMonitor;
import com.lbc.consistency.polling.Polling;
import com.lbc.consistency.polling.PolllingRefreshMonitor;
import com.lbc.consistency.polling.StatusAcquirer;
import com.lbc.maintenance.SimpleStatusInfoContainer;
import com.lbc.maintenance.StatusInfoContainer;
import com.lbc.support.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.Map;

/**
 * DefaultCacheContext,@see CacheContext接口的默认实现,lbc核心类之一，。
 *
 * DefaultCacheContext主要借助于spring容器的生命周期来管理@see Cache的生命周期：
 *  InitializingBean    ——  初始化，包括创建缓存对象、处理配置信息、事件监听器等；
 *  BeanPostProcessor   ——  执行缓存预加载；
 *  ApplicationListener ——  缓存创建以及初始化工作完成后相关事件，比如开启缓存一致性监控等等；
 *  DisposableBean      ——  缓存销毁；
 *
 * DefaultCacheContext维护了一个LocalCache对象，LocalCache对使用者开放，{@link com.lbc.LocalCache}
 * DefaultCacheContext维护了一个LbcConfiguration对象，LbcConfiguration包含了所有的用户配置信息,{@link com.lbc.config.LbcConfiguration}
 * DefaultCacheContext维护了一个ConsistencyMonitor对象,负责缓存数据一致性监控，{@link com.lbc.consistency.ConsistencyMonitor}
 * DefaultCacheContext维护了一个EventMulticaster对象,EventMulticaster负责事件传播，{@link com.lbc.context.event.EventMulticaster}
 * DefaultCacheContext维护了一个StatusInfoContainer对象,StatusInfoContainer负责维护本地缓存状态信息，{@link com.lbc.maintenance.StatusInfoContainer}
 *
 *
 * @author wufenyun
 */
public class DefaultCacheContext implements CacheContext, BeanPostProcessor,ApplicationContextAware,
        ApplicationListener<ContextRefreshedEvent>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCacheContext.class);
    private final Object startupShutdownMonitor = new Object();

    private ApplicationContext applicationContext;
    private LbcConfiguration lbcConfiguration;
    private LocalCache cache;
    private ConsistencyMonitor monitor;
    private EventMulticaster eventMulticaster = new SimpleEventMulticaster();
    private StatusInfoContainer statusInfoContainer = new SimpleStatusInfoContainer();


    @Override
    public void setConfiguration(LbcConfiguration lbcConfiguration) {
        this.lbcConfiguration = lbcConfiguration;
    }

    /*   *************************   生命周期之初始化缓存对象   *************************   */
    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public synchronized void init() {
        AssertUtil.notNull(applicationContext);
        AssertUtil.notNull(applicationContext.getAutowireCapableBeanFactory());

        newCacheInstance();

        registerListener();
    }

    /**
     * 向spring容器中生成cache实例，达到使用者开箱即用的效果
     */
    private void newCacheInstance() {
        cache = new LocalCache(this);
        ((DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory()).registerSingleton("cache", cache);

    }

    /**
     * 注册事件监听器
     */
    private void registerListener() {
        Map<String,EventListener> listenerList = applicationContext.getBeansOfType(EventListener.class);
        listenerList.forEach((key,entry)->{
            eventMulticaster.addListener(entry);
        });
    }

    /*   *************************   生命周期之缓存预加载   *************************   */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CacheLoader) {
            preLoading((CacheLoader<?, ?>) bean);
        }
        return bean;
    }

    @Override
    public <K, V> void preLoading(CacheLoader<K, V> cacheLoader) {
        AssertUtil.notNull(cacheLoader.preLoadingKey(),"预加载缓存key不能为空");

        try {
            K key = cacheLoader.preLoadingKey();
            List<V> initializedData = cacheLoader.load(key);
            cache.put(key, initializedData, cacheLoader);
            cache.registerCacheLoader(key,cacheLoader);

            logger.info("CacheLoader preLoading '{}' cached data record:{}", key, initializedData.size());
            logger.debug("CacheLoader preLoading '{}' cached data :{}", key, initializedData);
        } catch (Exception e) {
            throw new RuntimeException("preLoading data error",e);
        }
    }

    /*   *************************   生命周期之缓存创建及初始化完成执行操作   *************************   */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        synchronized (startupShutdownMonitor) {
            //防止ContextRefreshedEvent事件发生多次
            if ((null != event.getApplicationContext().getParent())) {
                return;
            }

            onCacheBuilt();
        }
    }

    @Override
    public void onCacheBuilt() {
        switch (lbcConfiguration.getMonitorConfig().getMonitorModel()) {
            case EVENT_ZK:
                initZKMonitor();
                break;
            case POLLING:
                initPollMonitor();
                break;
            default:
                break;
        }
    }

    /**
     * 轮询模式缓存监控器初始化
     */
    private void initPollMonitor() {
        this.monitor = new PolllingRefreshMonitor(this);
        StatusAcquirer sAcquirer = null;
        try {
            sAcquirer = applicationContext.getBean(StatusAcquirer.class);
        } catch (NoSuchBeanDefinitionException e) {
            logger.warn("The caching state collector(StatusAcquirer) is not created by the user, and the cache will not be refreshed.Please note that!!!!");
            return;
        }

        Polling pmonitor = (Polling) monitor;
        pmonitor.setStatusAcquirer(sAcquirer);
        monitor.startMonitoring();
    }

    /**
     * 事件驱动模式(zk实现)缓存监控器初始化
     */
    private void initZKMonitor() {
        this.monitor = new ZkCacheMonitor(this);
        monitor.startMonitoring();
    }

    /*   *************************   生命周期之销毁缓存   *************************   */
    @Override
    public void destroy() throws Exception {
        close();
    }

    @Override
    public void close() {
        if (null != monitor) {
            monitor.stopMonitor();
        }

        cache = null;

        getEventMulticaster().multicast(new CloseEvent());
    }

    /*   *************************   其他接口   *************************   */
    @Override
    public Cache getGlobalSingleCache() {
        return cache;
    }

    @Override
    public LbcConfiguration getConfiguration() {
        return lbcConfiguration;
    }

    @Override
    public EventMulticaster getEventMulticaster() {
        return eventMulticaster;
    }

    @Override
    public StatusInfoContainer getStatusInfoContainer() {
        return statusInfoContainer;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
