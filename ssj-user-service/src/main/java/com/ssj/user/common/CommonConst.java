package com.ssj.user.common;

public interface CommonConst {

	//interface field默认public static final

	/**
	 * token过期时间：1天
	 */
	int TOKEN_EXPIRE_TIME = 1*24*60*60;

	/**
	 * 请求头登录校验token
	 */
	String TOKEN = "token";

	/**
	 * 开关打开
	 */
	String SWITCH_ON = "1";

}
