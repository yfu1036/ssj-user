package com.ssj.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AccountUpdateRequest {

    @ApiModelProperty(value="id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value="账户类型")
    @NotBlank(message = "accountType不能为空")
    private String accountType;

    @ApiModelProperty(value="账户来源")
    @NotBlank(message = "accountSource不能为空")
    private String accountSource;

    @ApiModelProperty(value="账户id")
    @NotBlank(message = "accountId不能为空")
    private String accountId;

    @ApiModelProperty(value="账户注册地")
    private String accountRegister;

    @ApiModelProperty(value="账户登录密码")
    @NotBlank(message = "loginPassword不能为空")
    private String loginPassword;

    @ApiModelProperty(value="账户交易密码")
    private String payPassword;

    @ApiModelProperty(value="账户秘密信息")
    private String secret;

    @ApiModelProperty(value="是否生效")
    @NotNull(message = "isValid不能为空")
    private Short isValid;
}