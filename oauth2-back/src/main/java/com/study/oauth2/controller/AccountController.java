package com.study.oauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.oauth2.dto.account.PrincipalRespDto;
import com.study.oauth2.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
	
	private final AccountService accountService;
	
	@GetMapping("/principal")
	public ResponseEntity<?> getPrincipal() {
		return ResponseEntity.ok(accountService.getPrincipal());
	}
}



