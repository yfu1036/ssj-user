package com.ssj.user.mapper;

import com.ssj.user.model.UserAccountInfo;

public interface UserAccountInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAccountInfo record);

    int insertSelective(UserAccountInfo record);

    UserAccountInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAccountInfo record);

    int updateByPrimaryKey(UserAccountInfo record);
}