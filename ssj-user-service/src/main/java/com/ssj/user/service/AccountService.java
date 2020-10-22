package com.ssj.user.service;

import com.ssj.user.dto.request.AccountAddRequest;
import com.ssj.user.dto.request.AccountUpdateRequest;
import com.ssj.user.dto.response.AccountDetailResponse;
import com.ssj.user.dto.response.AccountListResponse;

import java.util.List;

public interface AccountService {

	/**
	 * 根据账户类型和密码获取账户列表
	 * @param userId
	 * @param accountType
	 * @return
	 */
	List<AccountListResponse> getAccountList(String userId, String accountType);

	/**
	 * 根据用户id和账户id查询账户详情
	 * @param userId
	 * @param id
	 * @return
	 */
	AccountDetailResponse getAccountDetail(String userId, Long id);

	/**
	 * 用户新增账户
	 * @param userId
	 * @param req
	 * @return
	 */
	Boolean addAccount(String userId, AccountAddRequest req);

	/**
	 * 更新账号
	 * @param userId
	 * @param req
	 * @return
	 */
	Boolean updateAccount(String userId, AccountUpdateRequest req);

	/**
	 * 删除账号
	 * @param userId
	 * @param id
	 * @return
	 */
	Boolean deleteAccount(String userId, Long id);
}
