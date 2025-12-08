package com.popjub.common.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.popjub.common.context.UserContext;

import lombok.extern.slf4j.Slf4j;

/**
 * JPA Auditing 을 위한 AuditorAware 구현
 * BaseEntity 의 createdBy, updatedBy에 자동으로 userId 주입
 */
@Slf4j
@Component
public class AuditorAwareImpl implements AuditorAware<Long> {
	@Override
	public Optional<Long> getCurrentAuditor() {
		try {
			UserContext userContext = UserContext.get();

			if (userContext == null) {
				log.warn("AuditorAwareImpl - 현재 인증된 사용자가 없습니다. createdBy/updatedBy는 null 로 설정됩니다.");
				return Optional.empty();
			}

			return Optional.of(userContext.getUserId());
		} catch (Exception e) {
			log.error("AuditorAwareImpl - 작업자 정보 조회 실패: {}", e.getMessage());
			return Optional.empty();
		}
	}
}
