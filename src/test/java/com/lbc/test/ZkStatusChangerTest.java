/**
 * Package: com.lbc.test
 * Description: 
 */
package com.lbc.test;

import org.junit.Test;

import com.lbc.refresh.event.support.ZkCacheStatusChanger;

/**
 * Description:  
 * Date: 2018年3月12日 下午5:32:16
 * @author wufenyun 
 */
public class ZkStatusChangerTest {

    private String connection = "127.0.0.1:2181";
    private ZkCacheStatusChanger statusChanger = new ZkCacheStatusChanger(connection,"category");

    @Test
    public void change() {
        statusChanger.updateStatus("category");
    }
    
}
