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
 **/
public class StatusListener implements EventListener<CacheRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StatusListener.class);

    private SimpleStatusInfoContainer simpleStatusInfoContainer;

    public StatusListener(SimpleStatusInfoContainer simpleStatusInfoContainer) {
        this.simpleStatusInfoContainer = simpleStatusInfoContainer;
    }

    public void logPrinting(StatusInfo statusInfo) {
        logger.info("refresh message:{}", statusInfo.toString());
    }

    @Override
    public void onEvent(Event event) {
        if(!(event instanceof CacheRefreshedEvent)) {
            return;
        }

        StatusInfo statusInfo = (StatusInfo) event.getSource();
        simpleStatusInfoContainer.updateStatusInfo(statusInfo);
        logPrinting(statusInfo);
    }
}