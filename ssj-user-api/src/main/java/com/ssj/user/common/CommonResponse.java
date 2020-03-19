package com.ssj.user.common;

import lombok.Data;

/**
 *  通用返回类
 * @author yfu
 *
 */
@Data
public class CommonResponse<T> {

	private Integer code;
	
	private String msg;
	
	private T data;
}
