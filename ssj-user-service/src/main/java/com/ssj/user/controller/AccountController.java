package com.ssj.user.controller;

import com.ssj.user.common.CommonConst;
import com.ssj.user.common.CommonResponse;
import com.ssj.user.dto.request.AccountAddRequest;
import com.ssj.user.dto.request.AccountUpdateRequest;
import com.ssj.user.dto.response.AccountDetailResponse;
import com.ssj.user.dto.response.AccountListResponse;
import com.ssj.user.service.AccountService;
import com.ssj.user.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "账户接口")
@RestController
@Slf4j
public class AccountController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AccountService accountService;

	@ApiOperation(value = "账户列表", notes = "返回该用户账户类型账户列表")
	@GetMapping("/api/auth/account/list")
	public CommonResponse<List<AccountListResponse>> getAccountList(
			@RequestParam(value = "accountType") String accountType,
			@RequestHeader (CommonConst.TOKEN)String token) {

		String userId = tokenService.getUserIdByToken(token);
		return CommonResponse.success(accountService.getAccountList(userId, accountType));
	}

	@ApiOperation(value = "账户详情", notes = "返回该用户账户详情")
	@GetMapping("/api/auth/account/detail")
	public CommonResponse<AccountDetailResponse> getAccountList(
			@Valid @RequestParam(value = "id")Long id,
			@RequestHeader (CommonConst.TOKEN)String token) {

		String userId = tokenService.getUserIdByToken(token);
		return CommonResponse.success(accountService.getAccountDetail(userId, id));
	}

	@ApiOperation(value = "新增账户")
	@PostMapping("/api/auth/account/add")
	public CommonResponse<Void> addAccount(
			@Valid @RequestBody AccountAddRequest req,
			@RequestHeader (CommonConst.TOKEN)String token) {

		String userId = tokenService.getUserIdByToken(token);
		Boolean addFlag = accountService.addAccount(userId, req);
		if(addFlag) {
			return CommonResponse.success();
		} else {
			return CommonResponse.fail();
		}
	}

	@ApiOperation(value = "更新账户")
	@PostMapping("/api/auth/account/update")
	public CommonResponse<Void> updateAccount(
			@Valid @RequestBody AccountUpdateRequest req,
			@RequestHeader (CommonConst.TOKEN)String token) {

		String userId = tokenService.getUserIdByToken(token);
		Boolean addFlag = accountService.updateAccount(userId, req);
		if(addFlag) {
			return CommonResponse.success();
		} else {
			return CommonResponse.fail();
		}
	}

	@ApiOperation(value = "删除账户")
	@PostMapping("/api/auth/account/delete")
	public CommonResponse<Void> deleteAccount(
			@RequestParam(value = "id")Long id,
			@RequestHeader (CommonConst.TOKEN)String token) {

		String userId = tokenService.getUserIdByToken(token);
		accountService.deleteAccount(userId, id);
		return CommonResponse.success();
	}

}
