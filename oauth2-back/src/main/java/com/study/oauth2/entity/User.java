package com.study.oauth2.entity;

import java.util.List;

import com.study.oauth2.dto.account.PrincipalRespDto;
import com.study.oauth2.security.PrincipalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private int userId;
	private String email;
	private String password;
	private String name;
	private String provider;
	
	private List<Authority> authorities;
	
	public PrincipalUser toPrincipal() {
		return PrincipalUser.builder()
				.userId(userId)
				.email(email)
				.password(password)
				.authorities(authorities)
				.build();
	}
	
	public PrincipalRespDto toPrincipalRespDto() {
		
		StringBuilder builder = new StringBuilder();
		authorities.forEach(authority -> {
			builder.append(authority.getRole().getRoleName() + ",");
		});
		builder.delete(builder.length() - 1, builder.length());
		
		return PrincipalRespDto.builder()
				.userId(userId)
				.email(email)
				.name(name)
				.authorites(builder.toString())
				.build();
	}
}
















