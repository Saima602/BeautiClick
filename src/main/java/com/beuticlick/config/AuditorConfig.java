package com.beuticlick.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditorConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of("SYSTEM_USER");
	}
//return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
}
