package com.popjub.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.popjub.common.annotation.CurrentUser;
import com.popjub.common.context.UserContext;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		if (!parameter.hasParameterAnnotation(CurrentUser.class)) {
			return false;
		}
		// 지원타입 정의: Long, UserContext 2개
		Class<?> parameterType = parameter.getParameterType();
		return parameterType.equals(Long.class)
			|| parameterType.equals(UserContext.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		Class<?> parameterType = parameter.getParameterType();

		// Long: userId 반환
		if (parameterType.equals(Long.class)) {
			return UserContext.getCurrentUserId();
		}
		// UserContext: 전체 정보 반환
		if (parameterType.equals(UserContext.class)) {
			return UserContext.get();
		}

		return null;
	}
}