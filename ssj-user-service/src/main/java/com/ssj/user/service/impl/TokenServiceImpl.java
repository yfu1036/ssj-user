package com.ssj.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssj.user.common.CommonException;
import com.ssj.user.enums.ErrorCodeEnum;
import com.ssj.user.model.UserInfo;
import com.ssj.user.service.TokenService;
import com.ssj.user.util.RedisJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisJedisUtil redisJedisUtil;

    @Override
    public String getUserIdByToken(String token) {
        String userStr = redisJedisUtil.getString(token);
        if(StringUtils.isBlank(userStr)) {
            throw new CommonException(ErrorCodeEnum.GET_USERINFO_ERROR.getCode(),
                    ErrorCodeEnum.GET_USERINFO_ERROR.getMsg());
        }

        try {
            UserInfo userInfo = JSONObject.toJavaObject(JSON.parseObject(userStr), UserInfo.class);
            if (null == userInfo || StringUtils.isBlank(userInfo.getUserId())) {
                throw new CommonException(ErrorCodeEnum.GET_USERINFO_ERROR.getCode(),
                        ErrorCodeEnum.GET_USERINFO_ERROR.getMsg());
            }
            return userInfo.getUserId();
        } catch (Exception e){
            log.error(token+" toJavaObject error", e);
            throw new CommonException(ErrorCodeEnum.GET_USERINFO_ERROR.getCode(),
                    ErrorCodeEnum.GET_USERINFO_ERROR.getMsg());
        }
    }
}
