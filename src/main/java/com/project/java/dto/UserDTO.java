package com.project.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReTypePassword() {
		return reTypePassword;
	}

	public void setReTypePassword(String reTypePassword) {
		this.reTypePassword = reTypePassword;
	}

	public String getGoogleAccountId() {
		return googleAccountId;
	}

	public void setGoogleAccountId(String googleAccountId) {
		this.googleAccountId = googleAccountId;
	}

	public String getFacebookAccountId() {
		return facebookAccountId;
	}

	public void setFacebookAccountId(String facebookAccountId) {
		this.facebookAccountId = facebookAccountId;
	}

	public Long getRoleID() {
		return roleID;
	}

	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}

	@JsonProperty("email")
    @NotBlank(message = "email is required ")
    private String email;

    @JsonProperty("name")
    @NotBlank(message = " name is required ")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("password")
    @NotBlank(message = " password is required")
    private String password;

    @JsonProperty("reTypePassword")
    @NotBlank(message = " reTypePassword is required")
    private String reTypePassword;

    @JsonProperty("googleAccountId")
    private String googleAccountId;

    @JsonProperty("facebookAccountId")
    private String facebookAccountId;

    @JsonProperty("roleID")
    @NotNull(message = "Role is not null ")
    private Long roleID;

    // Constructor for all fields
    public UserDTO(String email, String name, String phone, String password, String reTypePassword, 
                   String googleAccountId, String facebookAccountId, Long roleID) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.reTypePassword = reTypePassword;
        this.googleAccountId = googleAccountId;
        this.facebookAccountId = facebookAccountId;
        this.roleID = roleID;
    }

    // Default constructor
    public UserDTO() {}

    // Getters and setters omitted for brevity...
    

    // Static inner Builder class
    public static class Builder {
        private String email;
        private String name;
        private String phone;
        private String password;
        private String reTypePassword;
        private String googleAccountId;
        private String facebookAccountId;
        private Long roleID;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder reTypePassword(String reTypePassword) {
            this.reTypePassword = reTypePassword;
            return this;
        }

        public Builder googleAccountId(String googleAccountId) {
            this.googleAccountId = googleAccountId;
            return this;
        }

        public Builder facebookAccountId(String facebookAccountId) {
            this.facebookAccountId = facebookAccountId;
            return this;
        }

        public Builder roleID(Long roleID) {
            this.roleID = roleID;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(email, name, phone, password, reTypePassword, googleAccountId, facebookAccountId, roleID);
        }
    }

    // Static method to start building
    public Builder builder() {
        return new Builder();
    }
}
