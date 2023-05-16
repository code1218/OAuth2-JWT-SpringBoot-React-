package com.study.oauth2.dto.account;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrincipalRespDto {
	private int userId;
	private String email;
	private String name;
	private String authorites;
}
