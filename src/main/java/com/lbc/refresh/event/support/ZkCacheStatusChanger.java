/**
 * Package: com.lbc.refresh.event.support
 * Description: 
 */
package com.lbc.refresh.event.support;

import org.I0Itec.zkclient.ZkClient;

/**
 * Description:  
 * Date: 2018年3月12日 下午5:53:41
 * @author wufenyun 
 */
public class ZkCacheStatusChanger implements CacheStatusChanger {
    
    private ZkClient client;
    private String rootPath = "localBatchCache";
    
    public ZkCacheStatusChanger(String connection) {
        this.client = new ZkClient(connection);
    }
    
    @Override
    public void updateStatus(String key, Object data) {
        //client.delete("/" + rootPath + "/" + key);
        //client.createEphemeral("/" + rootPath + "/" + key, data);
        client.writeData("/" + rootPath + "/" + key, data);
    }

}
