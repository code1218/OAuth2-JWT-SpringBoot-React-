package com.study.oauth2.dto.auth;

import lombok.Data;

@Data
public class OAuth2ProviderMergeReqDto {

	private String email;
	private String password;
	private String provider;
}
