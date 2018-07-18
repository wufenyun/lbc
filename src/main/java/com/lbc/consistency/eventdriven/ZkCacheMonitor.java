/**
 * Package: com.lbc.refresh.event
 * Description: 
 */
package com.lbc.consistency.eventdriven;

import com.lbc.config.MonitorConfig;
import com.lbc.consistency.AbstractRefreshMonitor;
import com.lbc.consistency.eventdriven.support.LbcZkSerializer;
import com.lbc.consistency.eventdriven.support.ZkCacheStatusChanger;
import com.lbc.context.CacheContext;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 依赖于zookeeper实现的缓存数据一致性监控器
 *
 * @author wufenyun
 */
public class ZkCacheMonitor extends AbstractRefreshMonitor implements IZkDataListener {

    private ZkClient client;
    
    public ZkCacheMonitor(CacheContext context) {
        super(context);
        
        client = new ZkClient(getMonitorConfig().getZkConnection());
        client.setZkSerializer(new LbcZkSerializer());
       
        if(!client.exists(MonitorConfig.ROOTPATH)) {
            client.createPersistent(MonitorConfig.ROOTPATH);
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
        String subNode = getMonitorConfig().getYourZkDataNode();
        if(!subNode.startsWith("/")) {
            subNode = "/" + subNode;
        }
        return MonitorConfig.ROOTPATH + subNode;
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
