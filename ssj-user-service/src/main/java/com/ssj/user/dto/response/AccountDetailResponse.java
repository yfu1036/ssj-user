package com.ssj.user.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class AccountDetailResponse {
    private Long id;

    private String userId;

    private String accountType;

    private String accountSource;

    private String accountRegister;

    private String accountId;

    private String loginPassword;

    private String payPassword;

    private String secret;

}