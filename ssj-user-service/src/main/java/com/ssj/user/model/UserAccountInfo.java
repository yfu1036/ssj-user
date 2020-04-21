package com.ssj.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserAccountInfo {
    private Long id;

    private String userId;

    private String accountType;

    private String accountSource;

    private String accountRegister;

    private String accountId;

    private String loginPassword;

    private String payPassword;

    private String secret;

    private String source;

    private Short isValid;

    private Date createTime;

    private Date updateTime;

}