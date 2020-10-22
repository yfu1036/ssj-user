package com.ssj.user.controller;

import com.ssj.user.common.CommonConst;
import com.ssj.user.common.CommonResponse;
import com.ssj.user.dto.request.WxloginRequest;
import com.ssj.user.service.TokenService;
import com.ssj.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户接口")
@RestController
@Slf4j
public class UserController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "微信登录", notes = "微信登录")
	@PostMapping("/api/release/wxLogin")
	public CommonResponse<String> wxLogin(@Valid @RequestBody WxloginRequest wxReq) {
		String token = userService.wxLogin(wxReq);
		return CommonResponse.success(token);
	}

	@ApiOperation(value = "登录测试", notes = "登录测试")
	@GetMapping("/api/auth/loginTest")
	public CommonResponse<String> loginTest(@RequestParam String unionId) {
		String token = userService.loginTest(unionId);
		return CommonResponse.success(token);
	}

	@ApiOperation(value = "退出登录")
	@PostMapping("/api/release/logput")
	public CommonResponse<Void> wxLogin(@RequestHeader (CommonConst.TOKEN) String token) {
		userService.logout(token);
		return CommonResponse.success();
	}

}
