package com.project.java.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.java.responses.address.AddressResponse;
import com.project.java.responses.address.ProvinceResponse;

@RestController
public class GetProvincesController {
	@Value("${apiPublic}")
	private String apiPublic;

	@Cacheable(value = "provincesCache")
	@GetMapping("/address/provinces")
	public ResponseEntity<List<AddressResponse>> GetProvinces() throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(apiPublic, HttpMethod.GET, entity, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		ProvinceResponse provinceResponse = objectMapper.readValue(response.getBody(), ProvinceResponse.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			// Ánh xạ danh sách Province sang AddressResponse
			List<AddressResponse> addressResponses = provinceResponse
					.getData().stream().map(province -> new AddressResponse.Builder()
							.setAddressId(province.getProvinceId()).setAddressName(province.getProvinceName()).build())
					.collect(Collectors.toList());
			return ResponseEntity.ok(addressResponses);

		} else {
			throw new Exception("Không thể gọi API hoặc API trả về lỗi.");
		}
	}

}
