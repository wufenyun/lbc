/**
 * Package: com.lbc.query
 * Description: 
 */
package com.lbc.wrap.query;

/**
 * Description:  
 * Date: 2018年3月7日 上午11:10:45
 * @author wufenyun 
 */
public class Example<T> {
    
    private T origin;
    private boolean ignoreNull;
    
    
    public Example(T origin) {
        this.setOrigin(origin);
    }
    
    public static <T> Example<T> of(T origin) {
        return new Example<>(origin);
    }

    public T getOrigin() {
        return origin;
    }

    public void setOrigin(T origin) {
        this.origin = origin;
    }

    public boolean isIgnoreNull() {
        return ignoreNull;
    }

    public void setIgnoreNull(boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
    }
}
