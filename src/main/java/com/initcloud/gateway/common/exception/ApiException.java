package com.initcloud.gateway.common.exception;

import com.initcloud.gateway.common.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException{
	private Throwable ex;
	private final ErrorCode errorCode;
}