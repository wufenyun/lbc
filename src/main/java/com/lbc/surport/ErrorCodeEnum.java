/**
 * Package: com.qingwei.common.exception
 * Description: 
 */
package com.lbc.surport;

/**
 * Description:  
 * Date: 2017年6月25日 下午3:24:40
 * @author wufenyun 
 */
public enum ErrorCodeEnum {
	
	PARAM_INVALID("","");
	public final String code;
	public final String description;
	
	ErrorCodeEnum(String code,String description) {
		this.code = code;
		this.description = description;
	}
}
