package com.project.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class UserSignInDTO {

    @NotNull(message = "userName is required")
    @JsonProperty("name")
    private String userName;

    @NotNull(message = "password is required")
    @JsonProperty("password")
    private String password;

    @NotNull(message = "email is required")
    @JsonProperty("email")
    private String email;

    // Constructor for all fields
    public UserSignInDTO(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    // Default constructor
    public UserSignInDTO() {}

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Static inner Builder class
    public static class Builder {
        private String userName;
        private String password;
        private String email;

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserSignInDTO build() {
            return new UserSignInDTO(userName, password, email);
        }
    }

    // Static method to start building
    public Builder builder() {
        return new Builder();
    }
}
