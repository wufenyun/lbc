/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.test;

import java.util.Collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.lbc.Cache;
import com.lbc.config.CacheFactory;
import com.lbc.test.module.user.entity.UserEntity;
import com.lbc.wrap.Wrapper;


@SpringBootApplication()
@ComponentScan("com.lbc.test")
public class Application  {

    
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        CacheConfig fac = context.getBean(CacheConfig.class);
        System.out.println(fac);
        
        CacheFactory cacheFactory = context.getBean(CacheFactory.class);
        Cache<String,Category> cache = cacheFactory.openSingletonCache();
        Wrapper<Category> wrapper = cache.get("category", null);
        
        Cache<String,UserEntity> userCache = cacheFactory.openSingletonCache();
        Wrapper<UserEntity> userWrapper = userCache.get("user", null);
        
        Collection<UserEntity> coll = userWrapper.all();
        System.out.println(coll.size());
        Thread.sleep(10000000);
    }
    
}