/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.lbc.config.CacheFactory;


@SpringBootApplication()
@ComponentScan("com.lbc.test")
public class Application  {

    
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        CacheConfig fac = context.getBean(CacheConfig.class);
        System.out.println(fac);
        
        CacheFactory c = context.getBean(CacheFactory.class);
        System.out.println(c);
        Thread.sleep(10000000);
    }
    
}