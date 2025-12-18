package com.popjub.common.interceptor;

import org.springframework.stereotype.Component;

import com.popjub.common.context.UserContext;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FeignClientInterceptor implements RequestInterceptor {

	private static final String USER_ID_HEADER = "X-USER-ID";
	private static final String USER_ROLES_HEADER = "X-USER-ROLES";

	@Override
	public void apply(RequestTemplate template) {
		UserContext context = UserContext.get();

		if (context != null) {
			template.header(USER_ID_HEADER, String.valueOf(context.getUserId()));
			if (context.getRoles() != null && !context.getRoles().isEmpty()) {
				String roles = String.join(",", context.getRoles());
				template.header(USER_ROLES_HEADER, roles);
			}

			log.debug("Feign 요청에 UserContext 헤더 추가 - userId: {}, roles: {}",
				context.getUserId(), context.getRoles());
		} else {
			log.warn("Feign 요청 시 UserContext가 없습니다. 헤더가 추가되지 않습니다.");
		}
	}
}
