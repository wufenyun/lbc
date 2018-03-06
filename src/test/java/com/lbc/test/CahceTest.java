package com.lbc.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CahceTest {

    private Mapper mapper = new Mapper();
    
    @Test
    public void test() throws ExecutionException {
        Cache<String, String> cache = CacheBuilder.newBuilder().build(); 
        System.out.println(cache.get("hello", new Callable<String>(){

            @Override
            public String call() throws Exception {
                mapper.call();
                System.out.println("loda data,key:" + "hello");
                return null;
            }
            
        }));
    }
    
    @Test
    public void sss() {
        Category cate = new Category();
        cate.setCategoryId(111);
        cate.setLevel(1);
        
        
    }
    
    
    public static class Mapper {

        public String call() {
            System.out.println("Mapper invoke");
            return null;
        }
        
    }
}
