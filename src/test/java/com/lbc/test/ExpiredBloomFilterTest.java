package com.lbc.test;

import com.lbc.support.ExpiredBloomFilter;
import org.junit.Test;

/**
 * @description:
 * @author: wufenyun
 * @date: 2018-07-12 16
 **/
public class ExpiredBloomFilterTest extends TestBase {

    @Test
    public void test() throws InterruptedException {
        ExpiredBloomFilter fileter = new ExpiredBloomFilter(1000,10);
        fileter.add("1111111111111");
        fileter.add("2222222222222");
        fileter.add("3333333333333");
        fileter.add("1111111222222");
        System.out.println(fileter.contains("1111111111111"));
        System.out.println(fileter.getUseRate());
        Thread.sleep(10*1000);
        System.out.println(fileter.contains("1111111111111"));
    }
}