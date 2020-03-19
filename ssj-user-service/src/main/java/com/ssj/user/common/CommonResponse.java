package com.ssj.user.common;

import com.ssj.user.enums.ResponseCodeEnum;

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
		return new CommonResponse<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg(), null);
	}

	public static <T> CommonResponse<T> success(T data) {
		return new CommonResponse<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg(), data);
	}

	public static <T> CommonResponse<T> fail() {
		return new CommonResponse<>(ResponseCodeEnum.COMMON_ERROR.getCode(), ResponseCodeEnum.COMMON_ERROR.getMsg(), null);
	}
	
	public static <T> CommonResponse<T> fail(Integer code, String msg) {
		return new CommonResponse<>(code, msg, null);
	}
}
