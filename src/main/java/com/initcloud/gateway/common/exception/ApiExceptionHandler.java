package com.initcloud.gateway.common.exception;

import com.initcloud.gateway.common.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> throwApiException(ApiException e) {
		log.error("ApiException : {}", e.getErrorCode().getMessage());
		return ApiResponse.throwException(e);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> throwException(Exception e) {
		log.error("Exception : {}", e.getMessage());
		return ApiResponse.throwException(e);
	}
}
