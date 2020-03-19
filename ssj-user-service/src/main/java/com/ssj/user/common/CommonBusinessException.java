package com.ssj.user.common;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommonBusinessException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer exceptionCode;
	
	private String exceptionMsg;
	
	public CommonBusinessException(Integer exceptionCode, String exceptionMsg) {
		super(exceptionCode + ":" + exceptionMsg);
		this.exceptionCode = exceptionCode;
		this.exceptionMsg = exceptionMsg;
	}
}
