/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.lbc.CacheInitialization;
import com.lbc.cacheloader.CacheLoader;

/**
 * Description: Date: 2018年3月5日 上午11:06:00
 * 
 * @author wufenyun
 */
public class CacheFactoryBean implements FactoryBean<CacheFactory>, InitializingBean,CacheInitialization {

    private CacheFactory factory;
    private CacheConfiguration configuration;

    @Override
    public CacheFactory getObject() throws Exception {
        if (null == factory) {
            afterPropertiesSet();
        }
        return factory;
    }

    @Override
    public Class<?> getObjectType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isSingleton() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildCacheFactory();
    }

    private void buildCacheFactory() {
        factory = new DefaultCacheFactory();
        initialize();
    }

    @Override
    public void initialize() {
        Map<Object, CacheLoader<Object, Object>> registedMap = configuration.getRegistedMap();
        registedMap.forEach((k, v) -> {
            try {
                Collection<Object> initiaData = v.initialize(k);
                factory.openSingletonCache().put(k, initiaData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CacheConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CacheConfiguration configuration) {
        this.configuration = configuration;
    }
}
