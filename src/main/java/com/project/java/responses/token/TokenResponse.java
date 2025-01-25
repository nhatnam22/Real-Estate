package com.project.java.responses.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@lombok.Builder
public class TokenResponse {

    @JsonProperty("jwt_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private TokenResponse(Builder builder) {
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
    }

    public static class Builder {
        private String accessToken;
        private String refreshToken;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public TokenResponse build() {
            return new TokenResponse(this);
        }
    }

    // Optional: Getters if needed
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
