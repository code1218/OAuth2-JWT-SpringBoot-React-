package com.study.oauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.oauth2.dto.auth.OAuth2RegisterReqDto;
import com.study.oauth2.security.jwt.JwtTokenProvider;
import com.study.oauth2.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthService authService;
	
	@PostMapping("/oauth2/register")
	public ResponseEntity<?> oauth2Register(
			@RequestHeader(value="registerToken") String registerToken, 
			@RequestBody OAuth2RegisterReqDto oAuth2RegisterReqDto) {
		
		boolean validated = jwtTokenProvider.validateToken(jwtTokenProvider.getToken(registerToken));
		
		if (!validated) {
			// 토큰이 유효하지 않음
			return ResponseEntity.badRequest().body("회원가입 요청 시간이 초과하였습니다.");
		}
		
		return ResponseEntity.ok(authService.oAuth2Register(oAuth2RegisterReqDto));
	}
}








