/**
 * Package: com.lbc.refresh.event.support
 * Description: 
 */
package com.lbc.refresh.event.support;

import java.io.Serializable;
import java.util.Arrays;

import org.I0Itec.zkclient.ZkClient;

/**
 * Description:  
 * Date: 2018年3月12日 下午5:53:41
 * @author wufenyun 
 */
public class ZkCacheStatusChanger implements CacheStatusChanger {
    
    private ZkClient client;
    private String rootPath = "/localBatchCache";
    
    public ZkCacheStatusChanger(String connection) {
        this.client = new ZkClient(connection);
        client.setZkSerializer(new LbcZkSerializer());
    }
    
    @Override
    public void updateStatus(Object... keys) {
        //client.delete("/" + rootPath + "/" + key);
        //client.createEphemeral("/" + rootPath + "/" + key, data);
        SatatusData satatusData = new SatatusData(keys);
        client.writeData(rootPath, satatusData);
    }
    
    public static class SatatusData implements Serializable {
        
        private static final long serialVersionUID = 7538970849137378719L;
        
        private Object[] keys;
        private long updateTime;
        
        public SatatusData(Object... keys) {
            this.setKeys(keys);
            this.updateTime = System.currentTimeMillis();
        }
        
        @Override
        public String toString() {
            return "SatatusData [keys=" + Arrays.toString(keys) + ", updateTime=" + updateTime + "]";
        }

        public long getUpdateTime() {
            return updateTime;
        }
        
        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object[] getKeys() {
            return keys;
        }

        public void setKeys(Object[] keys) {
            this.keys = keys;
        }
        
        
    }

}
