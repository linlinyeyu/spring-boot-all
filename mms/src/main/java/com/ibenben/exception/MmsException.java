/**
 * 
 */
package com.ibenben.exception;

import com.ibenben.enumtuil.ErrorCodeEnum;

/**
 * @author sszheng
 *
 * Created on 2016年8月8日 上午11:47:40
 */
public class MmsException extends Exception {
	
	private ErrorCodeEnum errorCodeEnum;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param arg0
	 */
	public MmsException(String arg0) {
		super(arg0);
	}
	
	public MmsException(ErrorCodeEnum errorCodeEnum) {
		super(Integer.toString(errorCodeEnum.getValue()));
		this.errorCodeEnum = errorCodeEnum;
	}

	public ErrorCodeEnum getErrorCodeEnum() {
		return errorCodeEnum;
	}

	public void setErrorCodeEnum(ErrorCodeEnum errorCodeEnum) {
		this.errorCodeEnum = errorCodeEnum;
	}
	
}
