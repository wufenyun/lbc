/**
 * Package: com.lbc.refresh.event
 * Description: 
 */
package com.lbc.refresh.event;

import java.util.List;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.lbc.CacheContext;
import com.lbc.refresh.AbstractRefreshMonitor;
import com.lbc.refresh.event.support.LbcZkSerializer;
import com.lbc.refresh.event.support.ZkCacheStatusChanger.SatatusData;

/**
 * Description:  
 * Date: 2018年3月9日 下午5:10:59
 * @author wufenyun 
 */
public class ZkCacheMonitor extends AbstractRefreshMonitor implements IZkDataListener {

    private ZkClient client;
    private volatile KeeperState state = KeeperState.SyncConnected;
    
    public ZkCacheMonitor(CacheContext context) {
        super(context);
    }
    
    @Override
    public void doMonitor() {
        client = new ZkClient(context.getConfiguration().getZkConnection());
        client.setZkSerializer(new LbcZkSerializer());
        String path = context.getConfiguration().getRootPath();
        if(!client.exists(path)) {
            client.createPersistent(path);
        }
        
        client.subscribeDataChanges(path, this);
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        SatatusData statusData = (SatatusData) data;
        notifyRefresh(statusData.getKeys());
        System.out.println(dataPath + "has changed:" + statusData.toString());
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        System.out.println(dataPath+"deleted");
    }

    public void createPersistent(String path) {
        try {
            client.createPersistent(path, true);
        } catch (ZkNodeExistsException e) {
        }
    }

    public void createEphemeral(String path) {
        try {
            client.createEphemeral(path);
        } catch (ZkNodeExistsException e) {
        }
    }

    public void delete(String path) {
        try {
            client.delete(path);
        } catch (ZkNoNodeException e) {
        }
    }

    public List<String> getChildren(String path) {
        try {
            return client.getChildren(path);
        } catch (ZkNoNodeException e) {
            return null;
        }
    }

    public boolean isConnected() {
        return state == KeeperState.SyncConnected;
    }

    public void doClose() {
        client.close();
    }

    

}
