package com.lbc.test;

import com.alibaba.fastjson.JSON;

/**
 * @description:
 * @author: wufenyun
 * @date: 2018-06-27 10
 **/
public class TestBase {

    /**
     *打印信息
     * @param target
     */
    public static void print(Object target) {
        System.out.println(" ----------------------输出信息如下：---------------------- ");
        System.out.println(JSON.toJSON(target));
    }
}