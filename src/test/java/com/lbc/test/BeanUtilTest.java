/**
 * Package: com.lbc.support
 * Description: 
 */
package com.lbc.test;

import java.util.Map;

import org.junit.Test;

import com.lbc.support.BeanUtil;

/**
 * Description:  
 * Date: 2018年3月19日 上午11:42:52
 * @author wufenyun 
 */
public class BeanUtilTest {

    
    @Test
    public void test() {
        Cat category = new Cat();
        category.setCategoryId(111);
        category.setParam("aa");
        Map<String, Object> map = BeanUtil.getPVMapIgnoreNull(category);
        map.forEach((k,v)->System.out.println(k + "  " + v));
    }
    
    public static class Cat extends Category {
        private String param;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
        
    }
}
