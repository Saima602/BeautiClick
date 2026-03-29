package com.beuticlick.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuditorConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			// Fall back to SYSTEM if no authenticated user (e.g. during startup or public
			// endpoints)
			if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
				return Optional.of("SYSTEM");
			}

			return Optional.of(auth.getName()); // returns the logged-in user's email
		};
	}
}
