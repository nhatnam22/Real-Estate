package com.project.java.responses.authorizationServer;

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
public class AuthorizationResponse {
    private String uri;

    private AuthorizationResponse(Builder builder) {
        this.uri = builder.uri;
    }

    public String getUri() {
        return uri;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String uri;

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public AuthorizationResponse build() {
            return new AuthorizationResponse(this);
        }
    }

	public AuthorizationResponse(String uri) {
		super();
		this.uri = uri;
	}
}

