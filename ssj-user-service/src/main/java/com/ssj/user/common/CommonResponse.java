package com.ssj.user.common;

import com.alibaba.fastjson.JSON;
import com.ssj.user.enums.ErrorCodeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  通用返回类
 * @author yfu
 *
 */
@Data
@AllArgsConstructor
public class CommonResponse<T> {

	private Integer code;
	
	private String msg;
	
	private T data;
	
	public static <T> CommonResponse<T> success() {
		return new CommonResponse<>(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMsg(), null);
	}

	public static <T> CommonResponse<T> success(T data) {
		return new CommonResponse<>(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMsg(), data);
	}

	public static <T> CommonResponse<T> fail() {
		return new CommonResponse<>(ErrorCodeEnum.COMMON_ERROR.getCode(), ErrorCodeEnum.COMMON_ERROR.getMsg(), null);
	}
	
	public static <T> CommonResponse<T> fail(Integer code, String msg) {
		return new CommonResponse<>(code, msg, null);
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}
