package com.ssj.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "系统接口")
@RestController
public class SystemController {

	@ApiOperation(value = "获取系统版本号", notes = "获取系统版本号")
	@GetMapping("/api/release/version")
	public String version() {
		return "ssj-user1.0.0";
	}
	
}
