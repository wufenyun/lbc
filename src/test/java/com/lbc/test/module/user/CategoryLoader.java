/**
 * Package: com.lbc.test
 * Description: 
 */
package com.lbc.test.module.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbc.Cache;
import com.lbc.LocalCache;
import com.lbc.exchanger.CacheExchanger;
import com.lbc.test.module.user.entity.Category;

/**
 * Description:  
 * Date: 2018年3月5日 下午2:14:12
 * @author wufenyun 
 */
@Service
public class CategoryLoader implements CacheExchanger<String, Category> {

    @Autowired
    private LocalCache localCache;
    
    @Override
    public boolean needRefresh(String key) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Collection<Category> initialize() throws Exception {
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

	@Override
	public String initializeKey() {
		// TODO Auto-generated method stub
		return "category";
	}

    public LocalCache getLocalCache() {
        return localCache;
    }

    public void setLocalCache(LocalCache localCache) {
        this.localCache = localCache;
    }

}
