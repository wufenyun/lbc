/**
 * Package: com.qingwei.common.util.support
 * Description: 
 */
package com.lbc.surport;

import java.util.Collection;
import java.util.Map;

/**
 * Description:  断言工具类
 * Date: 2017年6月27日 下午2:38:58
 * @author wufenyun 
 */
public class AssertUtil {
	
	/** 
	 * Description:  断言表达式是否为true
	 * @param expression 表达式
	 * @param message 异常信息
	 */
	public static void isTrue(boolean expression,String message) {
		if(!expression) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/** 
	 * Description:  断言对象是否不为空
	 * @param obj 断言对象
	 * @param message 异常信息
	 */
	public static void notNull(Object obj,String message) {
		if(null == obj) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/** 
	 * Description:  断言对象是否不为空
	 * @param obj 断言对象
	 */
	public static void notNull(Object obj) {
		notNull(obj,"Assert failed:this argument is null");
	}
	
	/** 
	 * Description: 断言集合不为空
	 * @param collection 集合
	 * @param message 异常信息
	 */
	public static void notEmpty(Collection<?> collection,String message) {
		if(null == collection || collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/** 
	 * Description: 断言集合不为空
	 * @param collection 集合
	 */
	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection,"Assert failed:this collection empty");
	}
	
	/** 
	 * Description: 断言Class数组是否不为空
	 * @param array 数组
	 * @param message 异常信息
	 */
	public static void notEmpty(Class<?>[] array,String message) {
		if(array == null || array.length == 0) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/** 
     * Description: 断言Map不为空
     * @param map map
     */
    public static void notEmpty(Map<?,?> map) {
        notEmpty(map,"Assert failed:this collection empty");
    }
    
    /** 
     * Description: 断言Map不为空
     * @param map Map
     * @param message 异常信息
     */
    public static void notEmpty(Map<?,?> map,String message) {
        if(map == null || map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
	
	/** 
	 * Description: 断言字符串不为空
	 * @param str 字符串
	 * @param message 异常信息
	 */
	public static void hasLength(String str,String message) {
		if(null == str || str.length() == 0) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/** 
     * Description:  断言字符串不为空，且字符串不能为空格。(null,"","  ")将抛出异常
     * @param str 源字符串
     * @param message 异常信息
     */
    public static void notBlank(String str) {
        notBlank(str,"Assert failed:this argument is null or blank");
    }
	
	/** 
	 * Description:  断言字符串不为空，且字符串不能为空格。(null,"","  ")将抛出异常
	 * @param str 源字符串
	 * @param message 异常信息
	 */
	public static void notBlank(String str,String message) {
	    if(null == str || str.length() == 0) {
            throw new IllegalArgumentException(message);
        }
	   
	    boolean isWhiteStr = true;
	    for(int i=0;i<str.length();i++) {
	        if(!Character.isWhitespace(str.charAt(i))) {
	            isWhiteStr = false;
	            break;
	        }
	    }
	    if(isWhiteStr) {
	        throw new IllegalArgumentException(message);
	    }
	}
	
}
