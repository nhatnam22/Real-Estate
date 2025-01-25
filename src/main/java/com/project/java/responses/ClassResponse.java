package com.project.java.responses;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ClassResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("data")
    private Object data;

    private ClassResponse(Builder builder) {
        this.message = builder.message;
        this.status = builder.status;
        this.data = builder.data;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private HttpStatus status;
        private Object data;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ClassResponse build() {
            return new ClassResponse(this);
        }
    }
}
