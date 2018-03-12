/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc;

import java.util.Collection;

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
import com.lbc.refresh.StatusAcquirer;
import com.lbc.refresh.StatusMonitor;
import com.lbc.refresh.event.ZkCacheMonitor;
import com.lbc.refresh.polling.PollingMonitor;
import com.lbc.refresh.polling.PolllingRefreshMonitor;

/**
 * Description: 
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
    public Cache getGloableSingleCache() {
        return cache;
    }

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
        logger.info("spring容器启动完毕，开始缓存监控任务");
        switch(configuration.getMonitorModel()) {
        case EVNET_ZK:
            initZKMonitor();break;
        case POLLING:
            initPollMonitor();break;
        default:
            break;
        }
    }
    
    private void initPollMonitor() {
        this.monitor = new PolllingRefreshMonitor(this);
        StatusAcquirer sAcquirer = null;
        try {
            sAcquirer = applicationContext.getBean(StatusAcquirer.class);
        } catch (NoSuchBeanDefinitionException e) {
            logger.warn("用户未创建缓存状态获取器(StatusAcquirer)，缓存将不会刷新，请注意！！！！！！！！！");
            return;
        }
        PollingMonitor pmonitor = (PollingMonitor)monitor;
        pmonitor.setStatusAcquirer(sAcquirer);
        monitor.startMonitoring();
    }
    
    private void initZKMonitor() {
        this.monitor = new ZkCacheMonitor();
        monitor.startMonitoring();
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(LocalCache.class);
        logger.info("向容器中注册LocalCache BeanDefinition");
        registry.registerBeanDefinition("localCache", beanDefinition);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CacheExchanger) {
            if(null == cache) {
                this.cache = applicationContext.getBean(LocalCache.class);
            }
            CacheExchanger<?,?> cacheExchanger = (CacheExchanger<?,?>) bean;
            registLoaders(cacheExchanger);
            initialize(cacheExchanger);
        }
        return bean;
    }

    private void registLoaders(CacheExchanger<?,?> cacheExchanger) {
        logger.info("注册需要初始加载的缓存加载器：" + cacheExchanger.getClass());
        cache.regist(cacheExchanger.prelaodingKey(), cacheExchanger);
        cache.registExchangerMapping(cacheExchanger.getClass(), cacheExchanger);
    }

    public <K,V> void initialize(CacheExchanger<K,V> cacheExchanger) {
        logger.info("start to initialization cache");
        Collection<V> initiaData;
        try {
            initiaData = cacheExchanger.prelaoding();
            cache.put(cacheExchanger.prelaodingKey(), initiaData,cacheExchanger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
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
        cache = null;
    }


}
