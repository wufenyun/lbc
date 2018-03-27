/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.lbc.config.CacheConfiguration;
import com.lbc.exchanger.CacheExchanger;
import com.lbc.refresh.StatusMonitor;
import com.lbc.refresh.event.ZkCacheMonitor;
import com.lbc.refresh.polling.Polling;
import com.lbc.refresh.polling.PolllingRefreshMonitor;
import com.lbc.refresh.polling.StatusAcquirer;

/**
 * DefaultCacheContext,默认缓存上下文实现,lbc核心类之一
 * 1. 实现缓存预加载
 * 2. 负责缓存监控器管理
 * 3. 初始化Cache对象,解析相关配置信息
 * 
 * Date: 2018年3月5日 上午10:49:57
 * 
 * @author wufenyun
 */
public class DefaultCacheContext implements CacheContext, BeanPostProcessor, BeanDefinitionRegistryPostProcessor,
        ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCacheContext.class);
    
    private ApplicationContext applicationContext;
    private CacheConfiguration configuration;
    private LocalCache cache;
    private StatusMonitor monitor;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * spring容器启动完成后开始缓存监控任务
     * 
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //cache对象为空，说明不需要使用批量缓存功能
        if((null == cache)) {
            return;
        }
        
        //防止ContextRefreshedEvent事件发生多次
        if((null != event.getApplicationContext().getParent())) {
            return;
        }
        
        switch(configuration.getMonitorModel()) {
        case EVNET_ZK:
            initZKMonitor();break;
        case POLLING:
            initPollMonitor();break;
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
            logger.warn(
                    "The caching state collector(StatusAcquirer) is not created by the user, and the cache will not be refreshed.Please note that!!!!");
            return;
        }
        
        Polling pmonitor = (Polling)monitor;
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

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(LocalCache.class);
        logger.debug("regist LocalCache BeanDefinition");
        registry.registerBeanDefinition("localCache", beanDefinition);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CacheExchanger) {
            CacheExchanger<?,?> cacheExchanger = (CacheExchanger<?,?>) bean;
            registLoaders(cacheExchanger);
            prelaoding(cacheExchanger);
        }
        return bean;
    }

    /** 
     * 注册缓存加载器信息
     * @param cacheExchanger
     */
    private void registLoaders(CacheExchanger<?,?> cacheExchanger) {
        logger.info("regist caching loader that needs to be initially loaded：{}",cacheExchanger.getClass());
        cache.regist(cacheExchanger.prelaodingKey(), cacheExchanger);
        cache.registExchangerMapping(cacheExchanger.getClass(), cacheExchanger);
    }

    /** 
     * 预加载缓存数据
     * @param cacheExchanger
     */
    public <K,V> void prelaoding(CacheExchanger<K,V> cacheExchanger) {
        List<V> initiaData;
        try {
            initiaData = cacheExchanger.prelaoding();
            cache.put(cacheExchanger.prelaodingKey(), initiaData,cacheExchanger);
            logger.info("CacheExchanger preloading '{}' cached data record:{}",cacheExchanger.prelaodingKey(),initiaData.size());
            logger.debug("CacheExchanger preloading '{}' cached data :{}",cacheExchanger.prelaodingKey(),initiaData);
        } catch (Exception e) {
            logger.error("preloading data error",e);
        }
    }
    
    @Override
    public Cache getGloableSingleCache() {
        return cache;
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if(null == cache) {
            this.cache = applicationContext.getBean(LocalCache.class);
            cache.init(this);
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    @Override
    public CacheConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CacheConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void destroy() throws Exception {
        //关闭监控器
        if(null != monitor) {
            monitor.close();
        }
        
        cache = null;
    }


}
