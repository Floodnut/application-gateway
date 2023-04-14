package com.initcloud.gateway.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import com.initcloud.gateway.common.enums.ErrorCode;
import com.initcloud.gateway.common.exception.ApiException;

@Slf4j
@Getter
public class ApiResponse<T> {

	private char success;
	private T data;
	private ErrorDto error;

	@Builder
	public ApiResponse(char success, T data, ErrorDto error) {
		this.success = success;
		this.data = data;
		this.error = error;
	}

	public ApiResponse(@Nullable T data) {
		this.success = 'y';
		this.data = data;
		this.error = null;
	}

	public static ResponseEntity<ApiResponse> throwException(ApiException e) {
		return ResponseEntity.status(e.getErrorCode().getHttpStatus())
			.body(ApiResponse.builder().success('n').data(null).error(new ErrorDto(e.getErrorCode())).build());
	}

	public static ResponseEntity<ApiResponse> throwException(Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.builder().success('n').data(null).error(new ErrorDto(ErrorCode.ERROR_5001)).build());
	}
}
