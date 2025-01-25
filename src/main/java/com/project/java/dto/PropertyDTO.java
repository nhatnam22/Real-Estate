package com.project.java.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.java.model.Direction;
import com.project.java.model.PropertyListingType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {

    @JsonProperty("name")
    @NotBlank(message = "name is required")
    private String name;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("price")
    @NotNull(message = "price is required")
    private Integer price;
    
    @JsonProperty("address")
    @NotBlank(message = "address is required")
    private String address;
    
    @JsonProperty("poste-by")
    @NotNull(message = "posted-by is required")
    private String postedBy;
    
    @JsonProperty("email")
    @NotNull(message = "email is required")
    private String email;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("size")
    @NotNull(message = "size is required")
    private Integer size;
    
    @JsonProperty("images")
    @NotEmpty(message = "images is required and cannot be empty")
    private List<String> images; // or List<String> images
    
    @JsonProperty("listing-type")
    @NotNull(message = "listing-type is required")
    private PropertyListingType listingType;
    
    @JsonProperty("direction")
    private Direction direction;
    
    @JsonProperty("bedroom")
    private Integer bedroom;
    
    @JsonProperty("floor")
    private Integer floor;
    
    @JsonProperty("property-type")
    private String propertyType;

    @JsonProperty("bathroom")
    private Integer bathroom;

    @JsonProperty("year-built")
    private Integer yearBuilt;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty("expired-date")
    private LocalDateTime expiredDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty("expired-boost")
    private LocalDateTime expiredBoost;
    
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public PropertyListingType getListingType() {
		return listingType;
	}

	public void setListingType(PropertyListingType listingType) {
		this.listingType = listingType;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Integer getBedroom() {
		return bedroom;
	}

	public void setBedroom(Integer bedroom) {
		this.bedroom = bedroom;
	}

	public Integer getBathroom() {
		return bathroom;
	}

	public void setBathroom(Integer bathroom) {
		this.bathroom = bathroom;
	}

	public Integer getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(Integer yearBuilt) {
		this.yearBuilt = yearBuilt;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getImages() {
		return images;
	}
}
