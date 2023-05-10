package com.study.oauth2.dto.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.study.oauth2.entity.User;

import lombok.Data;

@Data
public class OAuth2RegisterReqDto {
	private String email;
	private String name;
	private String password;
	private String checkPassword;
	private String provider;
	
	public User toEntity() {
		return User.builder()
				.email(email)
				.name(name)
				.password(new BCryptPasswordEncoder().encode(password))
				.provider(provider)
				.build();
	}
}
