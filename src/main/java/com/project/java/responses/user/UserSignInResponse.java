package com.project.java.responses.user;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.java.model.Role;
import com.project.java.model.User;

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
public class UserSignInResponse {
    
    @JsonProperty("name")
    private String userName;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("roles")
    private Set<String> roles;
    
    @JsonProperty("jwt_token")
    private String accessToken;
    
    @JsonProperty("refresh_token")
    private String refreshToken;
    
//    public static UserSignInResponse toUserLogin(User user, String token) {
//    	return new Builder()
//    			.email(user.getEmail())
//    			.accessToken(token)
//    			.roles(user.getRoles().)
//    			.userName(user.getUsername())
//    			.build();
//    }

    private UserSignInResponse(Builder builder) {
        this.userName = builder.userName;
        this.email = builder.email;
        this.roles = builder.roles;
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
    }

    // Static inner Builder class
    public static class Builder {
        private String userName;
        private String email;
        private Set<String> roles;
        private String accessToken;
        private String refreshToken;

        public Builder() {}

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }
        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public UserSignInResponse build() {
            return new UserSignInResponse(this);
        }
    }

    // Getters (if needed)
    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
