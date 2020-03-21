package com.ssj.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WxloginRequest {

	@ApiModelProperty(value="微信code")
	@NotBlank(message = "code不能为空")
	private String code;

	@ApiModelProperty(value="微信昵称")
	private String nickName;
}
