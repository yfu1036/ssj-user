package com.ssj.user.common;

public interface CommonConst {

	//interface field默认public static final

	/**
	 * token前缀
	 */
	String TOKEN_PREFIX = "ssj-user_";

	/**
	 * token过期时间：一个月
	 */
	int TOKEN_EXPIRE_TIME = 30*24*60*60;

	/**
	 * 请求头登录校验token
	 */
	String TOKEN = "token";

	/**
	 * 开关打开
	 */
	String SWITCH_ON = "1";

}
