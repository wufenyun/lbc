/**
 * Package: com.lbc.refresh.event
 * Description: 
 */
package com.lbc.refresh.event;

import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.CacheContext;
import com.lbc.refresh.RefreshExecutor;
import com.lbc.refresh.Refresher;
import com.lbc.refresh.StatusMonitor;

/**
 * Description:  
 * Date: 2018年3月9日 下午5:10:59
 * @author wufenyun 
 */
public class ZkCacheMonitor implements StatusMonitor,IZkDataListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private Refresher refresher;
    private CacheContext cacheContext;
    private ZkClient client;
    private volatile KeeperState state = KeeperState.SyncConnected;
    
    public ZkCacheMonitor(CacheContext cacheContext) {
        this.cacheContext = cacheContext;
        refresher = new RefreshExecutor(cacheContext);
    }
    
    @Override
    public void startMonitoring() {
        client = new ZkClient(cacheContext.getConfiguration().getZkConnection());
        String path = cacheContext.getConfiguration().getRootPath();
        if(!client.exists(path)) {
            client.createPersistent(path, System.currentTimeMillis());
        }
        
        Map<?, ?> keyLoaders = cacheContext.getGloableSingleCache().getAllKeyMap();
        keyLoaders.forEach((k,v)->{
            String pt = path+"/" + k;
            logger.info("zk上创建节点:{}是否需要刷新",pt);
            client.createEphemeral(pt);
            client.subscribeDataChanges(pt, this);
        });
        
        //client.subscribeChildChanges(path, this);
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        System.out.println(dataPath + "has changed");
        //dataPath.
        refresher.refresh("key");
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        // TODO Auto-generated method stub
        
    }

    /*@Override
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        System.out.println(parentPath + "has changed");
        refresher.refresh("key");
    }*/

    
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
