/**
 * Package: com.lbc.test.base.config
 * Description: 
 */
package com.lbc.test.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbc.refresh.StatusAcquirer;
import com.lbc.test.module.user.dao.SignMapper;
import com.lbc.test.module.user.entity.Sign;

/**
 * Description:  
 * Date: 2018年3月9日 下午6:01:47
 * @author wufenyun 
 */
@Service
public class CacheStatusAcquirer implements StatusAcquirer {

    @Autowired
    private SignMapper signMapper;
    
    @Override
    public <K> boolean needRefresh(K key) {
        Sign sign = signMapper.get((String) key);
        return (null != sign && sign.getFlag() == 1);
    }

}
