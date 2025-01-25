package com.project.java.responses.address;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ProvinceResponse {
	private int error;

	@JsonProperty("error_text")
	private String errorText;

	@JsonProperty("data_name")
	private String dataName;

	private List<Province> data;

	// Getters và setters
	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public List<Province> getData() {
		return data;
	}

	public void setData(List<Province> data) {
		this.data = data;
	}

	public static class Province {
	    @JsonProperty("id")
	    private String provinceId;  // Ánh xạ trường "id" trong JSON vào provinceId

	    @JsonProperty("name")
	    private String provinceName;

	    @JsonProperty("name_en")
	    private String provinceNameEn;

	    @JsonProperty("full_name")
	    private String provinceFullName;

	    @JsonProperty("full_name_en")
	    private String provinceFullNameEn;

	    @JsonProperty("latitude")
	    private String latitude;

	    @JsonProperty("longitude")
	    private String longitude;

	    // Getters và setters
	    public String getProvinceId() {
	        return provinceId;
	    }

	    public void setProvinceId(String provinceId) {
	        this.provinceId = provinceId;
	    }

	    public String getProvinceName() {
	        return provinceName;
	    }

	    public void setProvinceName(String provinceName) {
	        this.provinceName = provinceName;
	    }

	    public String getProvinceNameEn() {
	        return provinceNameEn;
	    }

	    public void setProvinceNameEn(String provinceNameEn) {
	        this.provinceNameEn = provinceNameEn;
	    }

	    public String getProvinceFullName() {
	        return provinceFullName;
	    }

	    public void setProvinceFullName(String provinceFullName) {
	        this.provinceFullName = provinceFullName;
	    }

	    public String getProvinceFullNameEn() {
	        return provinceFullNameEn;
	    }

	    public void setProvinceFullNameEn(String provinceFullNameEn) {
	        this.provinceFullNameEn = provinceFullNameEn;
	    }

	    public String getLatitude() {
	        return latitude;
	    }

	    public void setLatitude(String latitude) {
	        this.latitude = latitude;
	    }

	    public String getLongitude() {
	        return longitude;
	    }

	    public void setLongitude(String longitude) {
	        this.longitude = longitude;
	    }
	}

}
