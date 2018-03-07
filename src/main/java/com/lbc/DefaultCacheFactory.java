/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
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
import com.lbc.schedule.RefreshTask;
import com.lbc.test.module.user.UserLoader;

/**
 * Description:  
 * Date: 2018年3月5日 上午10:49:57
 * @author wufenyun 
 */
public class DefaultCacheFactory implements CacheFactory,BeanDefinitionRegistryPostProcessor,ApplicationContextAware, InitializingBean,CacheInitialization,ApplicationListener<ContextRefreshedEvent>,DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCacheFactory.class);
    
    private Cache<Object, Object> cache = new LocalCache<>();
    private CacheConfiguration configuration = new CacheConfiguration();
    private ApplicationContext applicationContext;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(configuration.getRefreshThreads(), new ThreadFactory() {
        
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"localcache_refresher");
        }
    });
            
    @Override
    public Cache<Object, Object> openSingletonCache() {
        return cache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registLoaders();
        initialize();
    }

    private void registLoaders() {
        logger.info("start to regist cache");
        Map<String, CacheExchanger> map = applicationContext.getBeansOfType(CacheExchanger.class);
        map.forEach((name,loader)->
        {
            logger.info("注册需要初始加载的缓存加载器：" + loader.getClass());
            configuration.regist(loader.initializeKey(), loader);
        });
    }

    @Override
    public void initialize() {
        logger.info("start to initialization cache");
        Map<Object, CacheExchanger<Object, Object>> registedMap = configuration.getRegistedMap();
        registedMap.forEach((k, loader) -> {
            try {
                Collection<Object> initiaData = loader.initialize();
                cache.put(loader.initializeKey(), initiaData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

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

    /** 
     * spring容器启动完成后开始任务  
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //容器启动完成之后开始执行定时任务
        logger.info("spring容器启动完毕，开始启动定时任务");
        executor.scheduleWithFixedDelay(new RefreshTask(cache), configuration.getInitialDelay(), configuration.getIntervalMills(), TimeUnit.MILLISECONDS);
    }

    /*@Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition bd = beanFactory.getBeanDefinition("localCache");
        Object ca = beanFactory.createBean(LocalCache.class,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,true);
        beanFactory.registerSingleton("localCache", ca);
        System.out.println("创建localcache" + ca);
        System.out.println("创建BeanDefinition" + bd);
    }*/

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(LocalCache.class);
        registry.registerBeanDefinition("localCache", beanDefinition);
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // TODO Auto-generated method stub
        
    }
}
