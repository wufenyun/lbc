/**
 * Package: com.lbc.wrap
 * Description: 
 */
package com.lbc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * lru辅助类
 * Date: 2018年3月23日 上午11:39:46
 * @author wufenyun 
 */
public class LruSupport {

    private int cacheSizeThreshold = -1;
    private LinkedList<Object> lruKeyList = new LinkedList<>();
    private Map<Object,Integer> keyMap = new HashMap<>();
    
    public LruSupport(int cacheSizeThreshold) {
        this.cacheSizeThreshold = cacheSizeThreshold;
    }
    
    /** 
     * 获取需要被驱逐的key
     * @param key
     * @return
     */
    public Object getNeedReclaimedKey(Object key) {
        //判断是否存在此节点，存在则移动key节点到首节点，不存在则添加到首节点
        if(keyMap.containsKey(key)) {
            lruKeyList.remove(key);
        } else {
            keyMap.put(key, 1);
        }
        lruKeyList.addFirst(key);
        
        if(lruKeyList.size() > cacheSizeThreshold) {
            return lruKeyList.removeLast();
        }
        return null;
    }

    public int getCacheSizeThreshold() {
        return cacheSizeThreshold;
    }

    public void setCacheSizeThreshold(int cacheSizeThreshold) {
        this.cacheSizeThreshold = cacheSizeThreshold;
    }
}
