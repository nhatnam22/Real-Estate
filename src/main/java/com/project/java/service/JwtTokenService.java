package com.project.java.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.project.java.config.JwtFilterConfig;
import com.project.java.exception.CheckDataException;
import com.project.java.exception.InvalidTokenException;
import com.project.java.exception.TokenNoExpireAtException;
import com.project.java.model.User;
import com.project.java.repository.UserRepository;
import com.project.java.responses.token.TokenResponse;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class JwtTokenService {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

	@Value("${token.secretkey}")
	private String secretKey;

	private final UserRepository userRepository;

	public JwtTokenService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public String generateRefreshToken(User user) throws Exception {

		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		try {
			String RefreshToken = JWT.create().withClaim("user_id", user.getId()).withSubject(user.getEmail())
					.withExpiresAt(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000L)).sign(algorithm);

			return RefreshToken;
		} catch (Exception exp) {
			throw new InvalidTokenException("can not generate token");
		}
	}

	public String generateAccessToken(User user) throws Exception {
		Map<String, Object> claim = new HashMap<>();
		claim.put("user_id", user.getId());
		claim.put("roles", user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		try {
			String AccessToken = JWT.create().withPayload(claim).withSubject(user.getEmail())
					.withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L)).sign(algorithm);

			return AccessToken;
		} catch (Exception exp) {
			throw new InvalidTokenException("can not generate token");
		}
	}

	public boolean validateAccessToken(String AccessToken, User user) {
		try {
			logger.info("Starting token validation for user: {}", user.getEmail());
			Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
			JWTVerifier jwtVerify = JWT.require(algorithm).withSubject(user.getEmail()).build();
			DecodedJWT decodedJWT = jwtVerify.verify(AccessToken);

			logger.info("Decoded JWT: {}", decodedJWT.getClaims());

			// get user_id
			Integer userIdFromToken = decodedJWT.getClaim("user_id").asInt();
			if (userIdFromToken != null) {
			    logger.info("User ID from token as integer: {}", userIdFromToken);
			} else {
			    logger.error("Failed to decode user_id as integer");
			}
			Integer userIdFromUserDetails = user.getId().intValue();
			logger.info("Decoded user_id from token: {}", userIdFromToken);
			logger.info("User ID from UserDetails: {}", userIdFromUserDetails);
			
			if (!userIdFromUserDetails.equals(userIdFromToken)) {
				logger.error("User ID mismatch: {} != {}", user.getId(), decodedJWT.getClaim("user_id").asString());
				return false;
			}

			// check roles
			List<String> rolesFromToken = decodedJWT.getClaim("roles").asList(String.class);
			List<String> userRoles = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
			if (!rolesFromToken.containsAll(userRoles)) {
				logger.error("Roles mismatch. Token roles: {}, User roles: {}", rolesFromToken, userRoles);
				return false;
			}

			logger.info("Token validated successfully for user: {}", user.getEmail());
			return true;

		} catch (Exception exp) {
			logger.error("Error during token validation: ", exp);
			throw new InvalidTokenException(exp.getMessage());
		}
	}

	public Map<String, Object> extractClaim(String accessToken) {
		Map<String, Object> claims = new HashMap<>();
		DecodedJWT decodeJWT = JWT.decode(accessToken);

		// Lấy tất cả các claims
		decodeJWT.getClaims().forEach((key, value) -> {
			if (key.equals("roles")) {
				claims.put(key, value.asList(String.class)); // Lấy danh sách roles
			} else {
				claims.put(key, value);
			}
		});

		return claims;
	}

	public String getEmailFromExtract(String accessToken) {
		DecodedJWT decodeJWT = JWT.decode(accessToken);
		return decodeJWT.getSubject();
	}

	public TokenResponse generateAccessTokenFromRefreshToken(String refreshToken) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier jwtVerify = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = jwtVerify.verify(refreshToken);

		String email = decodedJWT.getSubject();

		if (decodedJWT.getExpiresAt().before(new Date())) {
			throw new TokenNoExpireAtException("Refresh token has expired");
		}

		User user = userRepository.findByEmail(email).orElseThrow(() -> new CheckDataException("User not found"));

		String storedRefreshToken = user.getRefreshAccessToken();
		if (!storedRefreshToken.equals(refreshToken)) {
			throw new CheckDataException("Invalid refresh token");
		}

		try {
			// Tạo refresh token mới và cập nhật vào database
			String newRefreshToken = generateRefreshToken(user);
			user.setRefreshAccessToken(newRefreshToken);
			userRepository.save(user);

			// Tạo access token mới
			String newAccessToken = generateAccessToken(user);

			// Trả về cả access token và refresh token mới
			return new TokenResponse.Builder().accessToken(newAccessToken).refreshToken(newRefreshToken).build();
		} catch (Exception e) {
			throw new InvalidTokenException("Failed to generate new tokens");
		}
	}

}