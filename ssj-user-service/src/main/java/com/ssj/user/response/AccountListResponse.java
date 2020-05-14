package com.ssj.user.response;

import lombok.Data;

import java.util.List;

@Data
public class AccountListResponse {
    private List<AccountResponse> accountList;
}
