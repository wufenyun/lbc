package com.lbc.query;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
   /* public Query addCriteria(CriteriaDefinition cri){
        //判断是否已经存在对某个属性的查询定义
        CriteriaDefinition exist = criterias.get(cri.getKey());
        if(null != exist) {
            logger.error("查询已经包含对{}属性的查询定义",cri.getKey());
            throw new RuntimeException();
        } else {
            criterias.put(cri.getKey(), cri);
        }
        return this;
    }*/
    
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
