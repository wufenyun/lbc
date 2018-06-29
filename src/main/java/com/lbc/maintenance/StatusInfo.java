package com.lbc.maintenance;

import java.util.List;
import java.util.Map;

/**
 * @description: 本地缓存状态信息
 * @author: wufenyun
 * @date: 2018-06-28 17
 **/
public class StatusInfo {
    /**
     * 最近更新时间，等于最后进行更新的key缓存
     */
    private long lastUpdateTime;

    /**
     *所有key的更新信息
     */
    private List<UpdateInfo> fullInfo;


    /**
     *  针对单个key的一次更新信息
     *
     */
    public static class UpdateInfo {
        /**
         * 更新key
         */
        private Object key;
        /**
         * 更新前记录条数
         */
        private long previousCount;
        /**
         * 更新后当前记录条数
         */
        private long currentCount;
        /**
         * 更新时间
         */
        private long updateTime;

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public long getPreviousCount() {
            return previousCount;
        }

        public void setPreviousCount(long previousCount) {
            this.previousCount = previousCount;
        }

        public long getCurrentCount() {
            return currentCount;
        }

        public void setCurrentCount(long currentCount) {
            this.currentCount = currentCount;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }

}