package com.ssj.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		log.info("/api/release/wxLogin入参:{}", wxReq);
		String token = userService.wxLogin(wxReq);
		log.info("/api/release/wxLogin返回token:{}", token);
		return CommonResponse.success(token);
	}
}
