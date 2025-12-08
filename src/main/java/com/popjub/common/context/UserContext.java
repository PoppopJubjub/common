package com.popjub.common.context;

import java.util.List;

import com.popjub.common.enums.ErrorCode;
import com.popjub.common.exception.CommonException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserContext {

	private Long userId;
	private String email;

	private static final ThreadLocal<UserContext> CONTEXT_HOLDER = new ThreadLocal<>();

	public static void set(UserContext context) {
		CONTEXT_HOLDER.set(context);
	}

	public static UserContext get() {
		return CONTEXT_HOLDER.get();
	}

	public static Long getCurrentUserId() {
		UserContext context = get();
		if (context == null) {
			throw new CommonException(ErrorCode.USER_CONTEXT_NOT_FOUND);
		}
		return context.getUserId();
	}

	public static void clear() {
		CONTEXT_HOLDER.remove();
	}
}
