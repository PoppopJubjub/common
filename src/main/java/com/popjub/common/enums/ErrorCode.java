package com.popjub.common.enums;

import com.popjub.common.exception.BaseErrorCode;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements BaseErrorCode {
	// -- 공통 --
	INVALID_INPUT_VALUE("입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
	METHOD_NOT_ALLOWED("허용되지 않은 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),
	INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

	// UserContext 관련
	USER_CONTEXT_NOT_FOUND("사용자 인증 정보를 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED),
	ROLE_FORBIDDEN("해당되는 권한이 없습니다.", HttpStatus.FORBIDDEN);

	private final String message;
	private final HttpStatus status;

	ErrorCode(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}
}
