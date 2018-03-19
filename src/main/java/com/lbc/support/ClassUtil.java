/**
 * Package: com.qingwei.commonl.clazz.uti
 * Description: 
 */
package com.lbc.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:  
 * Date: 2017年6月30日 下午12:36:19
 * @author wufenyun 
 */
public class ClassUtil {
    
    /** 
     * Description:  
     * @param clazz
     * @return
     */
    public static List<Method> getAllMethods(Class<?> clazz) {
        AssertUtil.notNull(clazz);
        List<Method> methods = new ArrayList<>();
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        Class<?> superClass = clazz.getSuperclass();
        while(null!=superClass && !(superClass==Object.class)) {
            methods.addAll(Arrays.asList(superClass.getDeclaredMethods()));
            superClass = superClass.getSuperclass();
        }
        return methods;
    }
    
    /** 
     * Description:  
     * @param clazz
     * @return
     */
    public static List<Field> getAllField(Class<?> clazz) {
        AssertUtil.notNull(clazz);
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superClass = clazz.getSuperclass();
        while(null!=superClass && !(superClass==Object.class)) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }
        return fields;
    }
}
