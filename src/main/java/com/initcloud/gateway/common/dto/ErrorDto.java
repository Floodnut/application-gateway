package com.initcloud.gateway.common.dto;

import com.initcloud.gateway.common.enums.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {
	private final int code;
	private final String message;

	@Builder
	public ErrorDto(ErrorCode res) {
		this.code = res.getCode();
		this.message = res.getMessage();
	}
}