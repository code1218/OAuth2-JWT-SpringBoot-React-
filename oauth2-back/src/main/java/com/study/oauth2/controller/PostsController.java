package com.study.oauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.oauth2.dto.posts.RegisterPostsReqDto;
import com.study.oauth2.service.PostsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostsController {
	
	private final PostsService postsService;
	
	@PostMapping("/post/register")
	public ResponseEntity<?> register(RegisterPostsReqDto registerPostsReqDto) {
		
		return ResponseEntity.ok(postsService.registerPosts(registerPostsReqDto));
	}
	
}
