/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.lbc.Cache;
import com.lbc.CacheFactory;
import com.lbc.LocalCache;
import com.lbc.test.module.user.UserLoader;
import com.lbc.test.module.user.entity.UserEntity;


@SpringBootApplication()
@ComponentScan("com.lbc.test.base.config,com.lbc.test.module.user,com.lbc.test")
@MapperScan("com.lbc.test")
public class Application  {
    
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        CacheFactory cacheFactory = context.getBean(CacheFactory.class);
        Cache<String,UserEntity> userCache = cacheFactory.openSingletonCache();
        //QueryingCollection<String,UserEntity> userWrapper = userCache.get("user", null);
        LocalCache loader = context.getBean(LocalCache.class);
        System.out.println("获取localcache  " + loader);
        UserLoader userLoader = context.getBean(UserLoader.class);
        System.out.println("获取userLoader  " + userLoader);
        //Collection<UserEntity> coll = userWrapper.all();
        //System.out.println(coll.size());
    }
    
}