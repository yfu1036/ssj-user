package com.ssj.user.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssj.user.common.CommonBusinessException;
import com.ssj.user.common.CommonResponse;
import com.ssj.user.enums.ResponseCodeEnum;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ControllerErrorAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	private CommonResponse<Void> handleException(Exception e) {
		log.error("返回失败,Exception", e);
		return CommonResponse.fail();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private CommonResponse<Void> handleException(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String message = error.getDefaultMessage();
		log.error("返回失败," + message, e);
		return CommonResponse.fail(ResponseCodeEnum.VALID_ERROR.getCode(), message);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CommonBusinessException.class)
	private CommonResponse<Void> handleException(CommonBusinessException e) {
		log.error("返回失败," + e.getExceptionCode() + e.getExceptionMsg(), e);
		return CommonResponse.fail(e.getExceptionCode(), e.getExceptionMsg());
	}
	
}
