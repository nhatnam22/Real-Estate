package com.project.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.java.exception.CheckDataException;
import com.project.java.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	private final UserRepository userRepository;

	public SecurityConfig(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Bean
	public UserDetailsService userDetailService() {
		return email -> userRepository.findByEmail(email).orElseThrow(() -> new CheckDataException("email no exist"));

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticateProvider = new DaoAuthenticationProvider();
		authenticateProvider.setUserDetailsService(userDetailService());
		authenticateProvider.setPasswordEncoder(passwordEncoder());
		return authenticateProvider;

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
