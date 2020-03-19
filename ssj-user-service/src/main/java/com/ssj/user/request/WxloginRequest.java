package com.ssj.user.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WxloginRequest {

	@NotBlank(message = "code不能为空")
	private String code;

	//@NotBlank(message = "微信昵称不能为空")
	private String nickName;
}
