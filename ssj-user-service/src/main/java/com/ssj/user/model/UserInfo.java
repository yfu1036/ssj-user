package com.ssj.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    private Long id;

    private String userId;

    private String realName;

    private String cardId;

    private String phoneNumber;

    private String unionId;

    private String nickName;

    private String password;

    private Short isValid;

    private Date createTime;

    private Date updateTime;

}