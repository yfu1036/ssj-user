package com.ssj.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserDictionary {
    private Long id;

    private String type;

    private String typeDesc;

    private String code;

    private String name;

    private String parentCode;

    private Date createTime;

    private Date updateTime;

}