/**
 * Package: com.qingwei.common.exception
 * Description: 
 */
package com.lbc.surport;

/**
 * Description:  
 * Date: 2017年6月25日 下午3:12:15
 * @author qingwei
 */
@SuppressWarnings("serial")
public class BeanException extends RuntimeException {

	/**
	 * 描述信息
	 */
	private String message;
	
	public BeanException(String message) {
		super(message);
	}
	
	public BeanException(String message,Throwable cause) {
		super(message,cause);
	}
	
	@Override
	public String toString() {
		return "BeanException [message=" + message + "]";
	}

	@Override
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
