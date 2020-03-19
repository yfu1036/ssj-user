package com.ssj.user.model;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfo {
    private Long id;

    private String userId;

    private String realName;

    private String cardId;

    private String phoneNumber;

    private String unionId;

    private String nickName;

    private Integer valid;

    private Date createTime;

    private Date updateTime;

}