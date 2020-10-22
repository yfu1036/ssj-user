package com.ssj.user.dto.response;

import lombok.Data;

@Data
public class AccountListResponse {

    private Long id;

    private String accountType;

    private String accountSource;

    private String accountRegister;

    private String accountId;

}
