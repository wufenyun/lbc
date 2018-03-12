package com.lbc.query;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:  
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public class Query {
    
    private final static Logger logger = LoggerFactory.getLogger(Query.class);
    
    private Criteria criteria;
    
    public Query() {
    }
    
    public Query(Criteria criteriaDefinition) {
        criteria = criteriaDefinition;
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
