package com.project.java.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.java.controller.UserController;
import com.project.java.dto.GetUserDTO;
import com.project.java.model.User;
import com.project.java.repository.UserRepository;
import com.project.java.service.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class JwtFilterConfig extends OncePerRequestFilter {

	@Value("${api}")
	private String api;

	private final JwtTokenService jwtTokenService;
	private final UserRepository userRepository;
	private final UserDetailsService userDetailsService;
	private static final Logger logger = LoggerFactory.getLogger(JwtFilterConfig.class);

	public JwtFilterConfig(JwtTokenService jwtTokenService, UserRepository userRepository,
			UserDetailsService userDetailsService) {
		super();
		this.jwtTokenService = jwtTokenService;
		this.userRepository = userRepository;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 logger.info("Request Path: {}", request.getServletPath());
		try {
			if(isBypassToken(request)) {
				logger.info("Bypassing token validation for URL: {}", request.getRequestURI());
				
			} else {
				String authHeader = request.getHeader("Authorization");
				logger.info("Required token validation for URL: {}", request.getRequestURI());
				if (authHeader == null || !authHeader.startsWith("Bearer")) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
					return;
				}
				String token = authHeader.substring(7);
				String email = jwtTokenService.getEmailFromExtract(token);
				if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					logger.info("Validating token for user: {}", email);
					User userDetails = (User) userDetailsService.loadUserByUsername(email);
					logger.info("Validating token for userID: {}", userDetails.getId() );
					if (jwtTokenService.validateAccessToken(token, userDetails)) {
						UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						logger.info("AuthenticationToken: {}", authenticationToken);
						authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
						Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					    User user = (User) authentication.getPrincipal();
					    logger.info("User retrieved: {}", user.getUsername());
					}
				}
			}
			filterChain.doFilter(request,response);
		} catch (Exception e) {
			logger.info("error", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
		}
		
	}

	private boolean isBypassToken(HttpServletRequest request) {
	    List<Pair<String, String>> listURLPass = Arrays.asList(
	        Pair.of("/auth/users/register", "POST"),
	        Pair.of("/auth/users/login", "POST"),
	        Pair.of("/auth/users/login/login-type/google", "GET"),
	        Pair.of("/address/provinces", "GET"),
	        Pair.of("/auth/users/login/exchange-code", "GET"),
	        Pair.of("/property/property-type", "GET"),
	        Pair.of("/property/direction/get-all", "GET")
	    );
	    String requestPath = request.getServletPath();
	    String requestMethod = request.getMethod();
	    logger.info("Checking bypass for Path: {}, Method: {}", requestPath, requestMethod);

	    for (Pair<String, String> bypassToken : listURLPass) {
	        // So khớp đường dẫn bắt đầu với (cho phép các tham số truy vấn hoặc path extension)
	        if (requestPath.startsWith(bypassToken.getFirst()) && requestMethod.equals(bypassToken.getSecond())) {
	            return true;
	        }
	    }
	    return false;
	}


}
