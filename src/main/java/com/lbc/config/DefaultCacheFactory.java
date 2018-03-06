/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.config;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.lbc.Cache;
import com.lbc.CacheInitialization;
import com.lbc.DefaultCache;
import com.lbc.cacheloader.CacheLoader;

/**
 * Description:  
 * Date: 2018年3月5日 上午10:49:57
 * @author wufenyun 
 */
public class DefaultCacheFactory implements CacheFactory,ApplicationContextAware, InitializingBean,CacheInitialization,DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCacheFactory.class);
    
    private Cache<Object, Object> cache = new DefaultCache<>();
    private CacheConfiguration configuration;
    private ApplicationContext applicationContext;
    
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
        Map<String, CacheLoader> map = applicationContext.getBeansOfType(CacheLoader.class);
        map.forEach((name,loader)->
        {
            logger.info("注册需要初始加载的缓存加载器：" + loader.getClass());
            configuration.regist(loader.initializeKey(), loader);
        });
    }

    @Override
    public void initialize() {
        logger.info("start to initialization cache");
        Map<Object, CacheLoader<Object, Object>> registedMap = configuration.getRegistedMap();
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
}
