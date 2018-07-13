package com.lbc.support;

import java.util.Collection;

/**
 * Description:  工具类
 * Date: 2017年6月27日 下午2:38:58
 * @author wufenyun
 */
public class CommonUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return (null == collection || collection.isEmpty());
    }

    public static boolean isEmpty(String str) {
        return (null == str || "".equals(str));
    }

    public static boolean isBlank(String str) {
        return (null == str || "".equals(str.trim()));
    }
}
