/**
 * Package: com.lbc.test
 * Description: 
 */
package com.lbc.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lbc.config.CacheConfiguration;
import com.lbc.config.DefaultCacheFactory;

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
        bean.setConfiguration(configuration);
        return bean;
    }
    
}
