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
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.lbc.CacheInitialization;
import com.lbc.cacheloader.CacheLoader;

/**
 * Description: Date: 2018年3月5日 上午11:06:00
 * 
 * @author wufenyun
 */
public class CacheFactoryBean implements FactoryBean<CacheFactory>,BeanFactoryPostProcessor, InitializingBean,CacheInitialization {

	private static final Logger logger = LoggerFactory.getLogger(CacheFactoryBean.class);
	
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
    	logger.info("start to initialization cache");
        Map<Object, CacheLoader<Object, Object>> registedMap = configuration.getRegistedMap();
        registedMap.forEach((k, loader) -> {
            try {
                Collection<Object> initiaData = loader.initialize();
                factory.openSingletonCache().put(loader.initializeKey(), initiaData);
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

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		logger.info("获取加载器：" );
		Map<?, CacheLoader> map = beanFactory.getBeansOfType(CacheLoader.class);
		map.forEach((k,v)->System.out.println("获取加载器：" + v.getClass()));
	}
}
