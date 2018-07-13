/**
 * Package: com.lbc.refresh.event.support
 * Description: 
 */
package com.lbc.consistency.eventdriven.support;

import com.lbc.config.MonitorConfig;
import org.I0Itec.zkclient.ZkClient;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Description:  
 * Date: 2018年3月12日 下午5:53:41
 * @author wufenyun 
 */
public class ZkCacheStatusChanger implements CacheStatusChanger {
    
    private ZkClient client;
    private String refresherDataNodePath;
    
    public ZkCacheStatusChanger(String connection) {
        this.client = new ZkClient(connection);
        client.setZkSerializer(new LbcZkSerializer());
        refresherDataNodePath = MonitorConfig.ROOTPATH + MonitorConfig.DEFAULT_ZKDATA_NODENAME;
    }
    
    /**
     * 为防止与其他应用key冲突,建议使用此构造函数指定refresherDataNodeName
     * @param connection
     * @param refresherDataNodeName zk数据节点名,需要更新的数据将会存储到这下面
     */
    public ZkCacheStatusChanger(String connection,String refresherDataNodeName) {
        this.client = new ZkClient(connection);
        client.setZkSerializer(new LbcZkSerializer());
        refresherDataNodePath = getYourDataNodePath(refresherDataNodeName);
    }
    
    private String getYourDataNodePath(String subNode) {
        if(null==subNode || subNode.isEmpty()) {
            return MonitorConfig.ROOTPATH + MonitorConfig.DEFAULT_ZKDATA_NODENAME;
        }
        
        if(!subNode.startsWith("/")) {
            subNode = "/" + subNode;
        }
        return MonitorConfig.ROOTPATH + subNode;
    }
    
    @Override
    public void updateStatus(Object... keys) {
        SatatusData satatusData = new SatatusData(keys);
        client.writeData(refresherDataNodePath, satatusData);
    }
    
    public static class SatatusData implements Serializable {
        
        private static final long serialVersionUID = 7538970849137378719L;
        
        private Object[] keys;
        private long updateTime;
        
        public SatatusData(Object... keys) {
            this.setKeys(keys);
            this.updateTime = System.currentTimeMillis();
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

        @Override
        public String toString() {
            return "SatatusData [keys=" + Arrays.toString(keys) + ", updateTime=" + updateTime + "]";
        }

        
    }

}
