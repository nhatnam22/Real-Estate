package com.project.java.responses.user;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.java.model.Role;
import com.project.java.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@lombok.Builder
public class UserResponse {
    @JsonProperty("user_id")
    private Long user_id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    @JsonProperty("roles")
    private Set<Role> roles;

    // Private constructor to enforce the use of the builder
    private UserResponse(Builder builder) {
        this.user_id = builder.user_id;
        this.name = builder.name;
        this.email = builder.email;
        this.facebookAccountId = builder.facebookAccountId;
        this.googleAccountId = builder.googleAccountId;
        this.roles = builder.roles;
    }

    // Static nested Builder class
    public static class Builder {
        private Long user_id;
        private String name;
        private String email;
        private String facebookAccountId;
        private String googleAccountId;
        private Set<Role> roles;

        public Builder() {}

        public Builder userId(Long user_id) {
            this.user_id = user_id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder facebookAccountId(String facebookAccountId) {
            this.facebookAccountId = facebookAccountId;
            return this;
        }

        public Builder googleAccountId(String googleAccountId) {
            this.googleAccountId = googleAccountId;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }

    // Getters for all fields
    public Long getUserId() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebookAccountId() {
        return facebookAccountId;
    }

    public String getGoogleAccountId() {
        return googleAccountId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public static UserResponse fromUser(User user) {
        return new Builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .facebookAccountId(user.getFacebookAccountId())
                .googleAccountId(user.getGoogleAccountId())
                .roles(user.getRoles())
                .build();
    }
}

