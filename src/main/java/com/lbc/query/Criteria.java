/**
 * Package: com.lbc.criteria
 * Description: 
 */
package com.lbc.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.support.PropertyUtil;

/**
 * Description:  
 * Date: 2018年3月6日 上午11:34:30
 * @author wufenyun 
 */
public class Criteria implements CriteriaDefinition {
    
    private static Logger logger = LoggerFactory.getLogger(Criteria.class);
    
    private String key;
    private List<Criteria> criteriaChain;
    private LinkedHashMap<Operator, Object> conditions = new LinkedHashMap<>();
    
    public Criteria() {
        criteriaChain = new ArrayList<>();
    }
    
    public Criteria(String key) {
        this.key = key;
        criteriaChain = new ArrayList<>();
        criteriaChain.add(this);
    }
    
    public Criteria(String key,List<Criteria> criteriaChain) {
        this.key = key;
        this.criteriaChain = criteriaChain;
        this.criteriaChain.add(this);
    }
    
    public static Criteria where(String key) {
        return new Criteria(key);
    }
    
    public Criteria and(String key) {
        return new Criteria(key,this.criteriaChain);
    }
    
    public Criteria is(Object value) {
        conditions.put(Operator.IS, value);
        return this;
    }
    
    public Criteria ne(Object value) {
        conditions.put(Operator.NE, value);
        return this;
    }
    
    public Criteria in(Collection<?> collection) {
        conditions.put(Operator.IN, collection);
        return this;
    }
    
    @Override
    public Object getCriteriaObject() {
        return conditions;
    }
    
    @Override
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public List<Criteria> getCriteriaChain() {
        return criteriaChain;
    }

    public void setCriteriaChain(List<Criteria> criteriaChain) {
        this.criteriaChain = criteriaChain;
    }

    public LinkedHashMap<Operator, Object> getConditions() {
        return conditions;
    }

    public void setOperations(LinkedHashMap<Operator, Object> conditions) {
        this.conditions = conditions;
    }

    public boolean matchThisCriNode(Object origin) {
        Object originvalue = PropertyUtil.getFieldValue(origin, key);
        for(Map.Entry<Operator, Object> condition:conditions.entrySet()) {
            //判断是否符合该条件，如果不符合，直接返回false
            boolean match = domatch(condition.getKey(),originvalue,condition.getValue());
            if(!match) {
                return false;
            }
        }
        return true;
    }
    
    private boolean domatch(Operator operator,Object origin,Object target) {
        switch(operator) {
        case IS:return origin.equals(target);
        case NE:return origin!=target;
        //case LT:return origin > target;
        case IN:return ((Collection<?>)target).contains(origin);
        default:return false;
        }
    }
    
    private static enum Operator {
        //相等操作符
        IS,
        //不相等操作符
        NE,
        //小于
        LT,
        //小于等于
        TLE,
        //包含
        IN;
    }
}
