package com.project.java.responses.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
	private String addressId;
	
	private String addressName;
	
    private AddressResponse(Builder builder) {
        this.addressId = builder.addressId;
        this.addressName = builder.addressName;
    }

    // Getter
    public String getAddressId() {
        return addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    // Static inner Builder class
    public static class Builder {
        private String addressId;
        private String addressName;

        // Setter cho addressId
        public Builder setAddressId(String addressId) {
            this.addressId = addressId;
            return this; // Trả về Builder để có thể gọi chuỗi phương thức
        }

        // Setter cho addressName
        public Builder setAddressName(String addressName) {
            this.addressName = addressName;
            return this;
        }

        // Phương thức build
        public AddressResponse build() {
            return new AddressResponse(this);
        }
    }

}
