package com.project.java.responses.property;

import com.project.java.model.PropertyListingType;
import com.project.java.model.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyResponse {
	private String name;
	private String description;
    private String province;
    private String district;
    private String ward;
    private PropertyListingType listingType;
    private PropertyType propertyType; 
    private Integer price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public PropertyListingType getListingType() {
		return listingType;
	}
	public void setListingType(PropertyListingType listingType) {
		this.listingType = listingType;
	}
	public PropertyType getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
}
