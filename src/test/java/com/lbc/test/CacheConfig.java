/**
 * Package: com.lbc.test
 * Description: 
 */
package com.lbc.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lbc.DefaultCacheFactory;
import com.lbc.config.CacheConfiguration;

/**
 * Description:  
 * Date: 2018年3月5日 下午2:03:17
 * @author wufenyun 
 */
@Configuration
public class CacheConfig {
	
    @Bean
    public DefaultCacheFactory config() throws Exception {
        DefaultCacheFactory bean = new DefaultCacheFactory();
        CacheConfiguration configuration = new CacheConfiguration();
        configuration.setIntervalMills(4000);
        configuration.setInitialDelay(4000);
        bean.setConfiguration(configuration);
        return bean;
    }
    
}
