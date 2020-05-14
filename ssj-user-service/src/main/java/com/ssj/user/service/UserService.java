package com.ssj.user.service;

import com.ssj.user.request.WxloginRequest;
import com.ssj.user.response.AccountListResponse;

public interface UserService {

	/**
	 * 微信登录，返回登录token
	 * @param wxReq
	 * @return
	 */
	String wxLogin(WxloginRequest wxReq);

	/**
	 * 根据账户类型和密码获取账户列表
	 * @param userId
	 * @param accountType
	 * @return
	 */
	AccountListResponse getAccountList(String userId, String accountType);
}
