package com.ssj.user.mapper;

import com.ssj.user.model.UserAccountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAccountInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAccountInfo record);

    int insertSelective(UserAccountInfo record);

    UserAccountInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAccountInfo record);

    int updateByPrimaryKey(UserAccountInfo record);

    /**
     * 根据userId+accountType查询账户列表
     * @param userId
     * @param accountType
     * @return
     */
    List<UserAccountInfo> selectByUserIdType(@Param(value = "userId")String userId,
                                             @Param(value = "accountType")String accountType);

    /**
     * 根据用户id和id查询
     * @param userId
     * @param id
     * @return
     */
    UserAccountInfo selectByIdUser(@Param(value = "userId")String userId,
                                   @Param(value = "id")Long id);

    /**
     * 根据用户id和账户id查询
     * @param userId
     * @param accountId
     * @return
     */
    UserAccountInfo selectByUserAccountId(@Param(value = "userId")String userId,
                                          @Param(value = "accountId")String accountId);
}