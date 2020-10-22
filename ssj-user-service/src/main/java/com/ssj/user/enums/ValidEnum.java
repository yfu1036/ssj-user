package com.ssj.user.enums;

import lombok.Getter;

@Getter
public enum ValidEnum {

	VALID(Short.valueOf("1"), "valid"),
	INVALID(Short.valueOf("0"), "invalid")
	;

	private Short code;
	private String msg;

	ValidEnum(Short code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
