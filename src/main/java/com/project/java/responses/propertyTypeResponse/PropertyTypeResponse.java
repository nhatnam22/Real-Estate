package com.project.java.responses.propertyTypeResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@lombok.Builder
public class PropertyTypeResponse {
	@JsonProperty("image")
	private String image;

	@JsonProperty("name")
	private String name;

	// Private constructor để chỉ cho phép tạo đối tượng qua Builder
	private PropertyTypeResponse(Builder builder) {
		this.image = builder.image;
		this.name = builder.name;
	}

	// Getter methods
	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	// Static inner Builder class
	public static class Builder {
		private String image;
		private String name;

		// Setter for `image` trả về chính Builder để hỗ trợ chaining
		public Builder image(String image) {
			this.image = image;
			return this;
		}

		// Setter for `name` trả về chính Builder để hỗ trợ chaining
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		// Build method để tạo đối tượng `PropertyTypeResponse`
		public PropertyTypeResponse build() {
			return new PropertyTypeResponse(this);
		}
	}
}
