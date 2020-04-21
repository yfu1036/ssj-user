package com.ssj.user.enums;

import lombok.Getter;

@Getter
public enum ResponseCodeEnum {

	SUCCESS(200, "success"),
	COMMON_ERROR(10001, "unknown error"),
	VALID_ERROR(10002, "param valid"),
	GET_WXINFO_ERROR(10003, "获取微信信息失败"),
	NOT_LOGINED(10004, "未登录")
	;
	
	private Integer code;
	private String msg;
	
	ResponseCodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
