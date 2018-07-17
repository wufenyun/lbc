package com.lbc.maintenance;

/**
 * @description: 针对单个key的状态信息
 * @author: wufenyun
 * @date: 2018-07-11 11
 **/
public class StatusInfo {

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

    @Override
    public String toString() {
        return "StatusInfo{" +
                "key=" + key +
                ", previousCount=" + previousCount +
                ", currentCount=" + currentCount +
                ", updateTime=" + updateTime +
                '}';
    }
}