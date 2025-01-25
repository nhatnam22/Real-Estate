package com.project.java.service;

import java.util.regex.*;

import org.springframework.stereotype.Service;

@Service
public class LocationService {
	public String extractWard(String address) {
	    Pattern pattern = Pattern.compile("(Xã|Phường) ([^,]+)");
	    Matcher matcher = pattern.matcher(address);
	    return matcher.find() ? matcher.group(2).trim() : null;
	}

	public String extractDistrict(String address) {
	    Pattern pattern = Pattern.compile("(Huyện|Quận) ([^,]+)");
	    Matcher matcher = pattern.matcher(address);
	    return matcher.find() ? matcher.group(2).trim() : null;
	}

	public String extractProvince(String address) {
	    Pattern pattern = Pattern.compile("(Tỉnh|Thành phố) ([^,]+)$");
	    Matcher matcher = pattern.matcher(address);
	    return matcher.find() ? matcher.group(2).trim() : null;
	}

}
