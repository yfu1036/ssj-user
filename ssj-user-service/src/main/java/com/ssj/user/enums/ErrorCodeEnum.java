package com.ssj.user.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	SUCCESS(200, "success"),
	COMMON_ERROR(10001, "unknown error"),
	VALID_ERROR(10002, "param valid"),
	GET_WXINFO_ERROR(10003, "获取微信信息失败"),
	NOT_LOGINED(10004, "未登录"),
	GET_USERINFO_ERROR(10005, "获取用户信息失败"),
	ACCOUNT_NOTEXIST(10006, "账户不存在"),
	ACCOUNT_EXISTED(10007, "账户已存在")
	;
	
	private Integer code;
	private String msg;
	
	ErrorCodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
