package com.ssj.user.common;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommonException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer exceptionCode;
	
	private String exceptionMsg;
	
	public CommonException(Integer exceptionCode, String exceptionMsg) {
		super(exceptionCode + ":" + exceptionMsg);
		this.exceptionCode = exceptionCode;
		this.exceptionMsg = exceptionMsg;
	}
}
