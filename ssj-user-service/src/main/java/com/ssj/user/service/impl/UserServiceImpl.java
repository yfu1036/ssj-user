package com.ssj.user.service.impl;

import java.util.UUID;

import com.ssj.user.component.RedisJedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.ssj.user.common.CommonBusinessException;
import com.ssj.user.common.CommonConst;
import com.ssj.user.component.RedisTemplateUtil;
import com.ssj.user.enums.ResponseCodeEnum;
import com.ssj.user.mapper.UserInfoMapper;
import com.ssj.user.model.UserInfo;
import com.ssj.user.request.WxloginRequest;
import com.ssj.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

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
	private RedisTemplateUtil redisTemplateUtil;

	@Autowired
	private RedisJedisUtil redisJedisUtil;

	@Value("${wechat.ssj.appid}")
	private String appId;

	@Value("${wechat.ssj.appsecret}")
	private String appSecret;

	@Override
	public String wxLogin(WxloginRequest wxReq) {
		//1.从微信后台获取用户信息
		String url = String.format(sessionUrl, appId, appSecret, wxReq.getCode());
		String userInfoString = restTemplate.getForObject(url, String.class);
		log.info("微信登录获取信息:{}", userInfoString);
		if(StringUtils.isBlank(userInfoString)) {
			throw new CommonBusinessException(ResponseCodeEnum.GET_WXINFO_ERROR.getCode(), 
					ResponseCodeEnum.GET_WXINFO_ERROR.getMsg());
		}
		
		//2.调用微信后台获取该用户unionId
		JSONObject wxJson = JSONObject.parseObject(userInfoString);
		String wxUnionId = null != wxJson.get("unionid") ? wxJson.get("unionid").toString() : wxJson.get("openid").toString();
		if(StringUtils.isBlank(wxUnionId)) {
			throw new CommonBusinessException(ResponseCodeEnum.GET_WXINFO_ERROR.getCode(), 
					ResponseCodeEnum.GET_WXINFO_ERROR.getMsg());
		}
		
		//3.新增或更新该用户信息
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
		
		//4.生成token放入redis并返回
		String token = UUID.randomUUID().toString().trim().replaceAll("-", "");
		redisTemplateUtil.set(token, JSONObject.toJSONString(userInfoExist), CommonConst.TOKEN_EXPIRE_TIME);
		
		return token;
	}

}
