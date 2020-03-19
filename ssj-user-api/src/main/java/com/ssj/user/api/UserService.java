package com.ssj.user.api;

import com.alibaba.fastjson.JSONObject;
import com.ssj.user.common.CommonResponse;

public interface UserService {

	public CommonResponse<JSONObject> queryUserInfoByWxId(String wxId);
}
