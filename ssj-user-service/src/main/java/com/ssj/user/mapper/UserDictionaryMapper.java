package com.ssj.user.mapper;


import com.ssj.user.model.UserDictionary;

public interface UserDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDictionary record);

    int insertSelective(UserDictionary record);

    UserDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDictionary record);

    int updateByPrimaryKey(UserDictionary record);
}