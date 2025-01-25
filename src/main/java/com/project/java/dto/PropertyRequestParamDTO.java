package com.project.java.dto;

import com.project.java.model.PropertyListingType;
import com.project.java.model.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequestParamDTO {
    private Long id;
    private String province;
    private String district;
    private String ward;
    private PropertyListingType listingType;
    private PropertyType propertyType; 
    private Integer price;
    private Integer page;
    private Integer size;
    private String sort;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public PropertyType getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}
}
