/**
 * Package: com.lbc.test
 * Description: 
 */
package com.lbc.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lbc.DefaultCacheContext;
import com.lbc.config.CacheConfiguration;
import com.lbc.config.CacheConfiguration.MonitorModel;

/**
 * Description:  
 * Date: 2018年3月5日 下午2:03:17
 * @author wufenyun 
 */
@Configuration
public class CacheConfig {
	
    @Bean
    public DefaultCacheContext config() throws Exception {
        DefaultCacheContext bean = new DefaultCacheContext();
        CacheConfiguration configuration = new CacheConfiguration();
        configuration.setIntervalMills(4000);
        configuration.setInitialDelay(4000);
        configuration.setMonitorModel(MonitorModel.EVNET_ZK);
        configuration.setZkConnection("127.0.0.1:2181");
        bean.setConfiguration(configuration);
        return bean;
    }
    
}
