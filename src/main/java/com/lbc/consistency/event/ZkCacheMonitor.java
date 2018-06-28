/**
 * Package: com.lbc.refresh.event
 * Description: 
 */
package com.lbc.consistency.event;

import com.lbc.consistency.AbstractRefreshMonitor;
import com.lbc.consistency.event.support.ZkCacheStatusChanger;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import com.lbc.CacheContext;
import com.lbc.config.CacheConfiguration.Constant;
import com.lbc.consistency.event.support.LbcZkSerializer;

/**
 * Description:  
 * Date: 2018年3月9日 下午5:10:59
 * @author wufenyun 
 */
public class ZkCacheMonitor extends AbstractRefreshMonitor implements IZkDataListener {

    private ZkClient client;
    
    public ZkCacheMonitor(CacheContext context) {
        super(context);
        
        client = new ZkClient(context.getConfiguration().getZkConnection());
        client.setZkSerializer(new LbcZkSerializer());
       
        if(!client.exists(Constant.ROOTPATH)) {
            client.createPersistent(Constant.ROOTPATH);
        }
    }
    
    @Override
    protected void doMonitor() {
        String path = getYourDataNodePath();
        if(!client.exists(path)) {
            client.createPersistent(path);
        }
        
        client.subscribeDataChanges(path, this);
    }
    
    private String getYourDataNodePath() {
        String subNode = context.getConfiguration().getYourZkDataNode();
        if(!subNode.startsWith("/")) {
            subNode = "/" + subNode;
        }
        return Constant.ROOTPATH + subNode;
    }
    
    @Override
    protected void doClose() {
        if(null != client) {
            client.close();
        }
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        ZkCacheStatusChanger.SatatusData statusData = (ZkCacheStatusChanger.SatatusData) data;
        notifyRefresh(statusData.getKeys());
        logger.debug("{} has changed:{}",dataPath, statusData.toString());
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        logger.info("{} has been deleted",dataPath);
    }


}
