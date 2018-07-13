package com.lbc.maintenance;

import com.lbc.context.event.CacheRefreshedEvent;
import com.lbc.context.event.Event;
import com.lbc.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地缓存状态信息监控器实现
 *
 *
 * @author: wufenyun
 * @date: 2018-06-28 18
 **/
public class StatusListener implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger(StatusListener.class);

    private StatusComponent statusComponent;

    public StatusListener(StatusComponent statusComponent) {
        this.statusComponent = statusComponent;
    }

    public StatusComponent getStatusComponent() {
        return null;
    }

    public void logPrinting(UpdateInfo updateInfo) {
        logger.info("refresh message:{}",updateInfo.toString());
    }

    @Override
    public void onEvent(Event event) {
        if(!(event instanceof CacheRefreshedEvent)) {
            return;
        }

        UpdateInfo updateInfo = (UpdateInfo) event.getSource();
        statusComponent.update(updateInfo.getKey(),updateInfo);
        logPrinting(updateInfo);
    }
}