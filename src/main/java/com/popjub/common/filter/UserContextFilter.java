package com.popjub.common.filter;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.popjub.common.context.UserContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserContextFilter extends OncePerRequestFilter {

	private static final String USER_ID_HEADER = "X-USER-ID";
	private static final String USER_NAME_HEADER = "X-USER-NAME";

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		try {
			String userIdHeader = request.getHeader(USER_ID_HEADER);
			String userNameHeader = request.getHeader(USER_NAME_HEADER);

			if (userIdHeader != null) {
				Long userId = Long.parseLong(userIdHeader);
				String userName = userNameHeader;

				// UserContext 에 저장 (ThreadLocal)
				UserContext context = UserContext.builder()
					.userId(userId)
					.userName(userName)
					.build();

				UserContext.set(context);

				log.debug("UserContext 설정 완료 - userId: {}, userName: {}", userId, userName);
			}

			// 다음 필터로
			filterChain.doFilter(request, response);

		} finally {
			// 요청 끝나면 ThreadLocal 정리 (메모리 누수 방지)
			UserContext.clear();
		}
	}
}
