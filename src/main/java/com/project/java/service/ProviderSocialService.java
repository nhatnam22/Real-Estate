package com.project.java.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.java.dto.UserDTO;

@Service
public class ProviderSocialService {
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleClientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.google.scope}")
	private String scope;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String googleRedirectUri;

	@Value("${spring.security.oauth2.client.provider.google.token-uri}")
	private String googleResponseToken;

	public String generateURLToProvider(String loginType) {
		if (loginType.trim().toLowerCase().equals("google")) {
			String authorizationUrl = "https://accounts.google.com/o/oauth2/auth" + "?client_id=" + googleClientId
					+ "&redirect_uri=" + googleRedirectUri + "&response_type=code" + "&scope=" + scope;
			return authorizationUrl;
		}
		if (loginType.trim().toLowerCase().equals("facebook")) {
			String authorizationUrl = "https://accounts.google.com/o/oauth2/auth" + "?client_id=" + googleClientId
					+ "&redirect_uri=" + googleRedirectUri + "&response_type=code" + "&scope=" + scope;
			return authorizationUrl;
		}
		return "no provider";
	}

	public String exchangeAccessToken(String authorizationCode) throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_secret", clientSecret);
		params.add("client_id", googleClientId);
		params.add("redirect_uri", googleRedirectUri);
		params.add("grant_type", "authorization_code");
		params.add("code", authorizationCode);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(googleResponseToken, HttpMethod.POST, request,
					String.class);
			System.out.println("Response Status Code: " + response.getStatusCode());
			System.out.println("Response Body: " + response.getBody());
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			String accessToken = jsonNode.path("access_token").asText();
			return accessToken;
		} catch (HttpClientErrorException e) {
			System.out.println("Error Status Code: " + e.getStatusCode());
			System.out.println("Error Response Body: " + e.getResponseBodyAsString());
			throw new Exception("Failed to exchange access token: " + e.getMessage(), e);
		}

	}

	public Map<String, Object> getUserInfo(String accessToken) throws Exception {
	    String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v1/userinfo";
	    RestTemplate restTemplate = new RestTemplate();
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(accessToken); 
	    
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    ResponseEntity<String> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, String.class);
	    
	    // Parse JSON để lấy thông tin người dùng
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode userInfo = objectMapper.readTree(response.getBody());
	    
	    Map<String, Object> userMap = new HashMap<>();
	    userMap.put("id", userInfo.path("sub").asText());
	    userMap.put("email", userInfo.path("email").asText());
	    userMap.put("name", userInfo.path("name").asText());
	    userMap.put("picture", userInfo.path("picture").asText());
	    

	    
	    return userMap;
	}

}
