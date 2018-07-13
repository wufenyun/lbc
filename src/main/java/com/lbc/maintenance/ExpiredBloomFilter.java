package com.lbc.maintenance;

import java.io.Serializable;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 布隆过滤器
 * @author: wufenyun
 * @date: 2018-07-11 14
 **/
public class ExpiredBloomFilter implements Serializable {
    private final int[] seeds = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53 };
    private final BitSet bitSet;
    private final int size;
    private final AtomicInteger useCount = new AtomicInteger(0);
    private final Double autoClearRate;
    /**
     * clearIntervalSeconds默认-1，即不开启
     */
    private int clearIntervalSeconds = -1;
    private long nextClearTime = -1;


    /**
     *
     * @param expectedInsertions 预期处理的数据规模
     * @param clearIntervalSeconds 清理所有记录信息的时间间隔
     */
    public ExpiredBloomFilter(int expectedInsertions,int clearIntervalSeconds) {
        this(expectedInsertions, clearIntervalSeconds,null);
    }

    /**
     *
     * @param expectedInsertions 预期处理的数据规模
     * @param clearIntervalSeconds 清理所有记录信息的时间间隔
     * @param autoClearRate 自动清空过滤器内部信息的使用比率，传null则表示不会自动清理，
     *            当过滤器使用率达到100%时，则无论传入什么数据，都会认为在数据已经存在了
     *            当希望过滤器使用率达到80%时自动清空重新使用，则传入0.8
     */
    public ExpiredBloomFilter(int expectedInsertions, int clearIntervalSeconds,Double autoClearRate) {
        this.size = optimalNumOfBits(expectedInsertions);
        bitSet = new BitSet(size);
        this.autoClearRate = autoClearRate;

        this.clearIntervalSeconds = clearIntervalSeconds;
        if(isOpenIntervalClear()) {
            this.nextClearTime = System.currentTimeMillis() + clearIntervalSeconds;
        }
    }

    private int optimalNumOfBits(int expectedInsertions) {
        long bitSize = this.seeds.length * expectedInsertions;
        if (bitSize < 0 || bitSize > Integer.MAX_VALUE) {
            throw new RuntimeException("位数太大溢出了，请降低误判率或者降低数据大小");
        }
        return (int) bitSize;
    }

    public void add(String data) {
        clearIfNeccesary();

        for (int i = 0; i < seeds.length; i++) {
            int index = hash(data, seeds[i]);
            setTrue(index);
        }
    }

    public boolean contains(String data) {
        if(clearIfNeccesary()) {
            return false;
        }

        for (int i = 0; i < seeds.length; i++) {
            int index = hash(data, seeds[i]);
            if(!bitSet.get(index)) {
                return false;
            }
        }
        return true;
    }

    public double getUseRate() {
        return (double) useCount.intValue() / (double) size;
    }

    private int hash(String data, int seeds) {
        char[] value = data.toCharArray();
        int hash = 0;
        if (value.length > 0) {
            for (int i = 0; i < value.length; i++) {
                hash = i * hash + value[i];
            }
        }

        hash = hash * seeds % size;
        // 防止溢出变成负数
        return Math.abs(hash);
    }

    private synchronized boolean clearIfNeccesary() {
        if(timeout() || rateOut()) {
            clear();
            return true;
        }
        return false;
    }

    private boolean timeout() {
        if(isOpenIntervalClear() && (System.currentTimeMillis() >= nextClearTime)) {
            nextClearTime = System.currentTimeMillis() + clearIntervalSeconds*1000;
            return true;
        }
        return false;
    }

    private boolean rateOut() {
        return (autoClearRate != null && (getUseRate() >= autoClearRate));
    }

    private boolean isOpenIntervalClear() {
        return (clearIntervalSeconds > 0);
    }

    public void setTrue(int index) {
        useCount.incrementAndGet();
        bitSet.set(index, true);
    }

    /**
     * 清空过滤器中的记录信息
     */
    private void clear() {
        useCount.set(0);
        bitSet.clear();
    }
}
