package com.lbc.query;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lbc.support.BeanUtil;

/**
 * Description:  
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public class Query {
    
    private Criteria criteria;
    
    public Query() {
    }
    
    public Query(Criteria criteriaDefinition) {
        criteria = criteriaDefinition;
    }
    
    public <T> Query(Example<T> example) {
        T source = example.getOrigin();
        Map<String, Object> nv = BeanUtil.getPVMapIgnoreNull(source);
        criteria = new Criteria();
        for(Entry<String, Object> entry:nv.entrySet()) {
            criteria.and(entry.getKey()).is(entry.getValue());
        }
    }
    
    public static <T> Query query(Example<T> example) {
        return new Query(example);
    }
    
    public static Query query(Criteria criteriaDefinition) {
        return new Query(criteriaDefinition);
    }
    
    public boolean predict(Object origin) {
        List<Criteria> criteriaChain = criteria.getCriteriaChain();
        for(Criteria cri : criteriaChain) {
            if(!cri.matchThisCriNode(origin)) {
                return false;
            }
        }
        return true;
    }
}
