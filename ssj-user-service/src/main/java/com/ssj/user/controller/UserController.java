package com.ssj.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.user.common.CommonConst;
import com.ssj.user.common.CommonResponse;
import com.ssj.user.enums.ResponseCodeEnum;
import com.ssj.user.model.UserInfo;
import com.ssj.user.request.WxloginRequest;
import com.ssj.user.response.AccountListResponse;
import com.ssj.user.service.UserService;
import com.ssj.user.util.RedisJedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户接口")
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisJedisUtil redisJedisUtil;

	@ApiOperation(value = "微信登录", notes = "微信登录")
	@PostMapping("/api/release/wxLogin")
	public CommonResponse<String> wxLogin(@Valid @RequestBody WxloginRequest wxReq) {
		String token = userService.wxLogin(wxReq);
		return CommonResponse.success(token);
	}

	@ApiOperation(value = "获取账户列表", notes = "返回该用户账户类型账户列表")
	@GetMapping("/api/auth/account/getAccountList")
	public CommonResponse<AccountListResponse> getAccountList(
			@Valid @RequestParam(value = "accountType") String accountType,
			@RequestHeader (CommonConst.TOKEN) String token) {

		String userId = getUserIdByToken(token);
		if(StringUtils.isBlank(userId)) {
			return CommonResponse.fail(ResponseCodeEnum.GET_USERINFO_ERROR.getCode(), ResponseCodeEnum.GET_USERINFO_ERROR.getMsg());
		}

		AccountListResponse resp = userService.getAccountList(userId, accountType);
		return CommonResponse.success(resp);
	}

	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 */
	private String getUserIdByToken(String token) {
		String userStr = redisJedisUtil.getString(token);
		if(StringUtils.isBlank(userStr)) {
			return null;
		}

		try {
			UserInfo userInfo = JSONObject.toJavaObject(JSON.parseObject(userStr), UserInfo.class);
			if (null != userInfo) {
				return userInfo.getUserId();
			}
			return null;
		} catch (Exception e){
			log.error(token+" toJavaObject error", e);
			return null;
		}
	}
}
