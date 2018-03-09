/**
 * Package: com.qingwei.commonl.bean.uti
 * Description: 
 */
package com.lbc.surport;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 属性工具类
 * Date: 2017年7月6日 上午10:26:40
 * @author wufenyun 
 */
public class PropertyUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);
    
    /** 
     * Description: 获取对象的指定属性值
     * @param source 源对象
     * @param fieldName 属性名
     * @return 属性的值
     */
    public static <T> Object getFieldValue(T source,String fieldName) {
        AssertUtil.notNull(source);
        AssertUtil.notBlank(fieldName);
        
        Class<?> sourceClass = source.getClass();
        PropertyDescriptor dsc;
        try {
            dsc = new PropertyDescriptor(fieldName, sourceClass);
            Method md = dsc.getReadMethod();
            return md.invoke(source);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("get field-{} value failed!",fieldName, e);
            throw new BeanException("get field value failed!", e);
        }
    }
    
    /** 
     * Description:  获取集合对象的指定属性名的值
     * @param sources 集合对象
     * @param fieldName 属性名
     * @return 属性值列表
     */
    public static <T> List<Object> getValuesFromList(List<T> sources,String fieldName) {
        AssertUtil.notEmpty(sources);
        AssertUtil.notBlank(fieldName);
        List<Object> list = new ArrayList<>();
        for(T source:sources) {
            list.add(getFieldValue(source,fieldName));
        }
        return list;
    }
}
