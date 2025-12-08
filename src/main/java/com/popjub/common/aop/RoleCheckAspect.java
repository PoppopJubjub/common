package com.popjub.common.aop;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.popjub.common.annotation.RoleCheck;
import com.popjub.common.context.UserContext;
import com.popjub.common.enums.ErrorCode;
import com.popjub.common.enums.UserRole;
import com.popjub.common.exception.CommonException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1) // UserContextFilter 다음에 실행
public class RoleCheckAspect {

	@Before("@annotation(roleCheck)")
	public void checkRoles(RoleCheck roleCheck) {
		List<String> roles = UserContext.getCurrentRoles();

		UserRole[] requiredRoles = roleCheck.value();

		List<String> requiredRoleNames = Arrays.stream(requiredRoles)
			.map(UserRole::name)
			.toList();

		boolean hasRole = requiredRoleNames.stream()
			.anyMatch(roles::contains);

		if (!hasRole) {
			throw new CommonException(ErrorCode.ROLE_FORBIDDEN);
		}
	}
}
