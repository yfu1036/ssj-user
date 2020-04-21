package com.ssj.user.controller;

import javax.validation.Valid;

import com.ssj.user.request.AccountListRequest;
import com.ssj.user.response.AccountListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ssj.user.common.CommonResponse;
import com.ssj.user.request.WxloginRequest;
import com.ssj.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "用户接口")
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "微信登录", notes = "微信登录")
	@PostMapping("/api/release/wxLogin")
	public CommonResponse<String> wxLogin(@Valid @RequestBody WxloginRequest wxReq) {
		String token = userService.wxLogin(wxReq);
		return CommonResponse.success(token);
	}

	@ApiOperation(value = "获取账户列表", notes = "密码认证，返回信息加密")
	@GetMapping("/api/auth/encrypt/getAccountList")
	public CommonResponse<AccountListResponse> getAccountList(
			@Valid
			@RequestParam(value = "password") String password) {
		AccountListResponse res = userService.getAccountList(password);
		return CommonResponse.success(res);
	}
}
