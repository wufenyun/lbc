package com.lbc.criteria;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query {
    
    private final static Logger logger = LoggerFactory.getLogger(Query.class);
    
    private final Map<String,CriteriaDefinition> criterias = new LinkedHashMap<>();
    
    public Query() {
    }
    
    public Query(CriteriaDefinition criteriaDefinition) {
        addCriteria(criteriaDefinition);
    }
    
    public static Query query(CriteriaDefinition criteriaDefinition) {
        return new Query(criteriaDefinition);
    }
    
    public Query addCriteria(CriteriaDefinition cri){
        //判断是否已经存在对某个属性的查询定义
        CriteriaDefinition exist = criterias.get(cri.getKey());
        if(null != exist) {
            logger.error("查询已经包含对{}属性的查询定义",cri.getKey());
            throw new RuntimeException();
        } else {
            criterias.put(cri.getKey(), cri);
        }
        return this;
    }
}
