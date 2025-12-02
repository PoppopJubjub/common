package com.popjub.common.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.popjub.common.enums.SuccessCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

	private String message;
	private SuccessCode code;
	private T data;

	// 단일 데이터 응답
	public static <T> ApiResponse<T> of(SuccessCode successCode, T data) {
		return ApiResponse.<T>builder()
			.code(successCode)
			.message(successCode.getMessage())
			.data(data)
			.build();
	}

	public static <T> ApiResponse<T> of(String message, T data) {
		return ApiResponse.<T>builder()
			.message(message)
			.data(data)
			.build();
	}

	// 리스트 응답
	public static <T> ApiResponse<List<T>> of(SuccessCode successCode, List<T> list) {
		return ApiResponse.<List<T>>builder()
			.code(successCode)
			.message(successCode.getMessage())
			.data(list)
			.build();
	}

	public static <T> ApiResponse<List<T>> of(String message, List<T> list) {
		return ApiResponse.<List<T>>builder()
			.message(message)
			.data(list)
			.build();
	}

	// 페이지 응답
	public static <T> ApiResponse<PageResponse<T>> of(SuccessCode successCode, Page<T> page) {
		return ApiResponse.<PageResponse<T>>builder()
			.code(successCode)
			.message(successCode.getMessage())
			.data(PageResponse.from(page))
			.build();
	}

	public static <T> ApiResponse<PageResponse<T>> of(String message, Page<T> page) {
		return ApiResponse.<PageResponse<T>>builder()
			.message(message)
			.data(PageResponse.from(page))
			.build();
	}
}
