package com.ssj.user.service;

import com.ssj.user.request.WxloginRequest;

public interface UserService {

	/**
	 * 微信登录，返回登录token
	 * @param wxReq
	 * @return
	 */
	public String wxLogin(WxloginRequest wxReq);

}
