/**
 * Package: com.lbc.test
 * Description: 
 */
package com.lbc.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lbc.cacheloader.CacheLoader;

/**
 * Description:  
 * Date: 2018年3月5日 下午2:14:12
 * @author wufenyun 
 */
public class CategoryLoader implements CacheLoader<String, Category> {

    @Override
    public boolean isNeedRefresh() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<Category> initialize(String key) throws Exception {
        List<Category> list = new ArrayList<>();
        Category c1 = new Category();
        c1.setCategoryId(111);
        list.add(c1);
        
        Category c2 = new Category();
        c2.setCategoryId(222);
        list.add(c2);
        return list;
    }

    @Override
    public Collection<Category> load(String key) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
