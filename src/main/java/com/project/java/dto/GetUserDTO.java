package com.project.java.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.project.java.model.Role;
import com.project.java.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDTO {
    private String username;
    private String email;
    private String phone;
    private Set<String> roles;

    public GetUserDTO(User user) {
        this.username = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
