/**
 * Package: com.lbc.test.base.config
 * Description: 
 */
package com.lbc.test.base.config;

import org.springframework.stereotype.Service;

import com.lbc.refresh.StatusAcquirer;

/**
 * Description:  
 * Date: 2018年3月9日 下午6:01:47
 * @author wufenyun 
 */
@Service
public class CacheStatusAcquirer implements StatusAcquirer {

    @Override
    public <K> boolean needRefresh(K key) {
        // TODO Auto-generated method stub
        return false;
    }

}
