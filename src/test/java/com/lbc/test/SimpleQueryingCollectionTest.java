/**
 * Package: com.lbc.test
 * Description: 
 */
package com.lbc.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lbc.query.Criteria;
import com.lbc.query.Example;
import com.lbc.query.Query;
import com.lbc.wrap.SimpleQueryingCollection;

/**
 * Description:  
 * Date: 2018年3月7日 上午11:10:45
 * @author wufenyun 
 */
public class SimpleQueryingCollectionTest {

    private SimpleQueryingCollection<Category> co = new SimpleQueryingCollection<Category>();
    
    @Test
    public void queryByCritreia() {
        Query query = Query.query(Criteria.where("categoryId").is(1)
                .and("level").is(1)
                .and("status").in(Arrays.asList(0,1)));
        
        List<Category> result = co.query(query);
        System.out.println(result.size());
    }
    
    @Test
    public void asMap() {
        System.out.println(co.asMap("categoryId"));
    }
    
    @Test
    public void queryByExample() {
        Category category = new Category();
        category.setStatus(1);
        Query query = Query.query(Example.of(category));
        
        List<Category> result = co.query(query);
        System.out.println(result.size());
    }
    

    @Before
    public void wrap() {
        Category c1 = new Category();
        c1.setCategoryId(1);
        c1.setLevel(1);
        c1.setStatus(1);
        
        Category c2 = new Category();
        c2.setCategoryId(2);
        c2.setLevel(2);
        c2.setStatus(1);
        
        List<Category> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        co.wrap(list);
    }
    
}
