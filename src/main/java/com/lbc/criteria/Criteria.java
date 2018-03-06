/**
 * Package: com.lbc.criteria
 * Description: 
 */
package com.lbc.criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:  
 * Date: 2018年3月6日 上午11:34:30
 * @author wufenyun 
 */
public class Criteria implements CriteriaDefinition {
    private String key;
    private Object value;
    private List<Criteria> criteriaChain;
    
    public Criteria() {
        criteriaChain = new ArrayList<Criteria>();
    }
    
    public Criteria(String key) {
        this.key = key;
        criteriaChain = new ArrayList<Criteria>();
        criteriaChain.add(this);
    }
    
    public static Criteria is(Object value) {
        
    }
    
    
    @Override
    public Object getCriteriaObject() {
        return value;
    }
    @Override
    public String getKey() {
        return key;
    }
}
