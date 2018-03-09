/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.test;

import java.util.Collection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lbc.Cache;
import com.lbc.CacheContext;
import com.lbc.LocalCache;
import com.lbc.test.module.user.entity.UserEntity;
import com.lbc.wrap.QueryingCollection;


@SpringBootApplication()
@MapperScan("com.lbc.test")
public class Application {
    
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        CacheContext cacheFactory = context.getBean(CacheContext.class);
        Cache cache1 = cacheFactory.openSingletonCache();
        //QueryingCollection<String,UserEntity> userWrapper = userCache.get("user", null);
        Cache cache2 = context.getBean(LocalCache.class);
        QueryingCollection<String,UserEntity> col = cache2.get("user");
        Collection<UserEntity> co = col.data();
        co.forEach((v)->System.out.println(v.getName()));
        System.out.println("获取localcache  " + cache1 + "     " + cache2);
        //Collection<UserEntity> coll = userWrapper.all();
        //System.out.println(coll.size());
    }
    
}