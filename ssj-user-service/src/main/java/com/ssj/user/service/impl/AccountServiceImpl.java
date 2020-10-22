package com.ssj.user.service.impl;

import com.ssj.user.common.CommonException;
import com.ssj.user.dto.request.AccountAddRequest;
import com.ssj.user.dto.request.AccountUpdateRequest;
import com.ssj.user.dto.response.AccountDetailResponse;
import com.ssj.user.dto.response.AccountListResponse;
import com.ssj.user.enums.ErrorCodeEnum;
import com.ssj.user.enums.ValidEnum;
import com.ssj.user.mapper.UserAccountInfoMapper;
import com.ssj.user.mapper.UserInfoMapper;
import com.ssj.user.model.UserAccountInfo;
import com.ssj.user.service.AccountService;
import com.ssj.user.util.AESUtil;
import com.ssj.user.util.RedisJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserAccountInfoMapper userAccountInfoMapper;

	@Autowired
	private RedisJedisUtil redisJedisUtil;

	@Override
	public List<AccountListResponse> getAccountList(String userId, String accountType) {
		//1:查询该用户账户类型账户列表
		List<UserAccountInfo> accountExtList = userAccountInfoMapper.selectByUserIdType(userId, accountType);
		if(CollectionUtils.isEmpty(accountExtList)) {
			return null;
		}

		//2:组装账户列表
		List<AccountListResponse> accountRespList = new ArrayList<AccountListResponse>();
		for(UserAccountInfo account : accountExtList) {
			AccountListResponse resp = new AccountListResponse();
			BeanUtils.copyProperties(account, resp);
			//accountId解密后脱敏
			resp.setAccountId(AESUtil.decryptBasedAes(resp.getAccountId()));
			if(resp.getAccountId().length() >= 6) {
				int index = resp.getAccountId().length()/4;
				resp.setAccountId(resp.getAccountId().substring(0, index) + "****" +
						resp.getAccountId().substring(index + 4));
			}
			accountRespList.add(resp);
		}

		return accountRespList;
	}

	@Override
	public AccountDetailResponse getAccountDetail(String userId, Long id) {
		UserAccountInfo accountInfo = userAccountInfoMapper.selectByIdUser(userId, id);
		if(null == accountInfo) {
			throw new CommonException(ErrorCodeEnum.ACCOUNT_NOTEXIST.getCode(),
					ErrorCodeEnum.ACCOUNT_NOTEXIST.getMsg());
		}
		AccountDetailResponse resp = new AccountDetailResponse();
		BeanUtils.copyProperties(accountInfo, resp);
		//解密
		resp.setAccountId(AESUtil.decryptBasedAes(resp.getAccountId()));
		resp.setLoginPassword(AESUtil.decryptBasedAes(resp.getLoginPassword()));
		resp.setPayPassword(AESUtil.decryptBasedAes(resp.getPayPassword()));
		resp.setSecret(AESUtil.decryptBasedAes(resp.getSecret()));
		return resp;
	}

	@Override
	public Boolean addAccount(String userId, AccountAddRequest req) {
		//accountId不能重复
		UserAccountInfo accountInfoExt = userAccountInfoMapper.selectByUserAccountId(userId, AESUtil.encryptBasedAes(req.getAccountId()));
		if(null != accountInfoExt) {
			throw new CommonException(ErrorCodeEnum.ACCOUNT_EXISTED.getCode(),
					ErrorCodeEnum.ACCOUNT_EXISTED.getMsg());
		}
		UserAccountInfo accountInfo = new UserAccountInfo();
		BeanUtils.copyProperties(req, accountInfo);
		accountInfo.setUserId(userId);
		accountInfo.setIsValid(ValidEnum.VALID.getCode());
		//加密
		accountInfo.setAccountId(AESUtil.encryptBasedAes(accountInfo.getAccountId()));
		accountInfo.setLoginPassword(AESUtil.encryptBasedAes(accountInfo.getLoginPassword()));
		accountInfo.setPayPassword(AESUtil.encryptBasedAes(accountInfo.getPayPassword()));
		accountInfo.setSecret(AESUtil.encryptBasedAes(accountInfo.getSecret()));
		int insertRow = userAccountInfoMapper.insertSelective(accountInfo);
		if(insertRow > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateAccount(String userId, AccountUpdateRequest req) {
		UserAccountInfo accountInfoExt = userAccountInfoMapper.selectByIdUser(userId, req.getId());
		if(null == accountInfoExt) {
			throw new CommonException(ErrorCodeEnum.ACCOUNT_NOTEXIST.getCode(),
					ErrorCodeEnum.ACCOUNT_NOTEXIST.getMsg());
		}
		BeanUtils.copyProperties(req, accountInfoExt);
		accountInfoExt.setUserId(userId);
		//加密
		accountInfoExt.setAccountId(AESUtil.encryptBasedAes(accountInfoExt.getAccountId()));
		accountInfoExt.setLoginPassword(AESUtil.encryptBasedAes(accountInfoExt.getLoginPassword()));
		accountInfoExt.setPayPassword(AESUtil.encryptBasedAes(accountInfoExt.getPayPassword()));
		accountInfoExt.setSecret(AESUtil.encryptBasedAes(accountInfoExt.getSecret()));
		int updateRow = userAccountInfoMapper.updateByPrimaryKeySelective(accountInfoExt);
		if(updateRow > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean deleteAccount(String userId, Long id) {
		UserAccountInfo accountInfo = userAccountInfoMapper.selectByIdUser(userId, id);
		if(null == accountInfo) {
			throw new CommonException(ErrorCodeEnum.ACCOUNT_NOTEXIST.getCode(),
				ErrorCodeEnum.ACCOUNT_NOTEXIST.getMsg());
		}
		int deleteRow = userAccountInfoMapper.deleteByPrimaryKey(id);
		if(deleteRow > 0) {
			return true;
		} else {
			return false;
		}
	}

}
