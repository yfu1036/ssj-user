package com.ssj.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ssj.user.common.CommonException;
import com.ssj.user.common.CommonConst;
import com.ssj.user.dto.request.WxloginRequest;
import com.ssj.user.enums.ErrorCodeEnum;
import com.ssj.user.mapper.UserInfoMapper;
import com.ssj.user.model.UserInfo;
import com.ssj.user.service.UserService;
import com.ssj.user.util.RedisJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Value("${wechat.jscode2session.url}")
	private String sessionUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RedisJedisUtil redisJedisUtil;

	@Value("${wechat.ssj.appid}")
	private String appId;

	@Value("${wechat.ssj.appsecret}")
	private String appSecret;

	@Override
	public String wxLogin(WxloginRequest wxReq) {
		//1 从微信后台获取用户信息
		String url = String.format(sessionUrl, appId, appSecret, wxReq.getCode());
		String userInfoString = restTemplate.getForObject(url, String.class);
		log.info("微信登录获取信息:{}", userInfoString);
		JSONObject wxJson = JSONObject.parseObject(userInfoString);
		if(StringUtils.isBlank(userInfoString) || StringUtils.isNotBlank(wxJson.get("errcode").toString())) {
			throw new CommonException(ErrorCodeEnum.GET_WXINFO_ERROR.getCode(),
					ErrorCodeEnum.GET_WXINFO_ERROR.getMsg());
		}
		
		//2 调用微信后台获取该用户unionId
		String wxUnionId = null != wxJson.get("unionid") ? wxJson.get("unionid").toString() : wxJson.get("openid").toString();
		if(StringUtils.isBlank(wxUnionId)) {
			throw new CommonException(ErrorCodeEnum.GET_WXINFO_ERROR.getCode(),
					ErrorCodeEnum.GET_WXINFO_ERROR.getMsg());
		}

		//3 新增或更新该用户信息
		UserInfo userInfoExist = userInfoMapper.selectByUnionId(wxUnionId);
		if(null == userInfoExist) {
			userInfoExist = new UserInfo();
			userInfoExist.setUserId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
			userInfoExist.setUnionId(wxUnionId);
			userInfoExist.setNickName(StringUtils.isNotEmpty(wxReq.getNickName()) ? wxReq.getNickName() : "用户" + System.currentTimeMillis());
			userInfoMapper.insertSelective(userInfoExist);
		} else {
			if(StringUtils.isNotEmpty(wxReq.getNickName()) && 
					!userInfoExist.getNickName().equals(wxReq.getNickName())) {
				userInfoExist.setNickName(wxReq.getNickName());
				userInfoMapper.updateByPrimaryKeySelective(userInfoExist);
			}
		}
		
		//4 生成token放入redis并返回
		String token = CommonConst.TOKEN_PREFIX+UUID.randomUUID().toString().trim().replaceAll("-", "");
		redisJedisUtil.setStringEx(token, CommonConst.TOKEN_EXPIRE_TIME, JSONObject.toJSONString(userInfoExist));
		
		return token;
	}

	@Override
	public String loginTest(String unionId) {
		UserInfo userInfoExist = userInfoMapper.selectByUnionId(unionId);
		if(null == userInfoExist) {
			userInfoExist = new UserInfo();
			userInfoExist.setUserId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
			userInfoExist.setUnionId(unionId);
			userInfoExist.setNickName("用户" + System.currentTimeMillis());
			userInfoMapper.insertSelective(userInfoExist);
		}
		String token = CommonConst.TOKEN_PREFIX+UUID.randomUUID().toString().trim().replaceAll("-", "");
		redisJedisUtil.setStringEx(token, CommonConst.TOKEN_EXPIRE_TIME, JSONObject.toJSONString(userInfoExist));
		return token;
	}

	@Override
	public void logout(String token) {
		redisJedisUtil.delKey(token);
	}

}
