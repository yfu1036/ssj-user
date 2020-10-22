package com.ssj.user.service;

import com.ssj.user.dto.request.WxloginRequest;

public interface UserService {

	/**
	 * 微信登录，返回登录token
	 * @param wxReq
	 * @return
	 */
	String wxLogin(WxloginRequest wxReq);

	/**
	 * 登录测试
	 * @param userId
	 * @return
	 */
	String loginTest(String unionId);

	/**
	 * 退出登录
	 * @param token
	 */
	void logout(String token);

}
