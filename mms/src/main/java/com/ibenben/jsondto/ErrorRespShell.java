/**
 * 
 */
package com.ibenben.jsondto;

import com.ibenben.enumtuil.ErrorCodeEnum;

/**
 * @author sszheng
 *
 */
public class ErrorRespShell {
	private int errorCode;
	private String errorMesseage;
	
	public ErrorRespShell(int errorCode){
		this.errorCode = errorCode;
		this.errorMesseage = ErrorCodeEnum.getText(errorCode);
	}
	
	public ErrorRespShell(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMesseage = errorMessage;
	}
	
	public ErrorRespShell(ErrorCodeEnum errorCodeEnum) {
		this.errorCode = errorCodeEnum.getValue();
		this.errorMesseage = errorCodeEnum.getText();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMesseage() {
		return errorMesseage;
	}

	public void setErrorMesseage(String errorMesseage) {
		this.errorMesseage = errorMesseage;
	}
	
}
