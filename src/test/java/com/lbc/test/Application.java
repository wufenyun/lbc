/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.test;

import java.util.Collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.lbc.Cache;
import com.lbc.config.CacheFactory;
import com.lbc.test.module.user.entity.UserEntity;
import com.lbc.wrap.QueryingCollection;


@SpringBootApplication()
@ComponentScan("com.lbc.test")
public class Application  {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        CacheFactory cacheFactory = context.getBean(CacheFactory.class);
        Cache<String,UserEntity> userCache = cacheFactory.openSingletonCache();
        QueryingCollection<UserEntity> userWrapper = userCache.get("user", null);
        
        Collection<UserEntity> coll = userWrapper.all();
        System.out.println(coll.size());
    }
    
}