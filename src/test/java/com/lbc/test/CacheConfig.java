/**
 * Package: com.lbc.test
 * Description: 
 */
package com.lbc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lbc.config.CacheConfiguration;
import com.lbc.config.CacheFactory;
import com.lbc.config.CacheFactoryBean;
import com.lbc.test.module.user.dao.UserMapper;

/**
 * Description:  
 * Date: 2018年3月5日 下午2:03:17
 * @author wufenyun 
 */
@Configuration
public class CacheConfig {
	
	@Autowired
	private UserLoader userLoader;
	
    @Bean
    public CacheFactory config() throws Exception {
        CacheFactoryBean bean = new CacheFactoryBean();
        CacheConfiguration configuration = new CacheConfiguration();
        configuration.regist("category", new CategoryLoader());
        configuration.regist("user", userLoader);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }
}
