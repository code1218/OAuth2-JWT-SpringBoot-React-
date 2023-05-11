package com.study.oauth2.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.study.oauth2.dto.auth.OAuth2ProviderMergeReqDto;
import com.study.oauth2.dto.auth.OAuth2RegisterReqDto;
import com.study.oauth2.entity.Authority;
import com.study.oauth2.entity.User;
import com.study.oauth2.repository.UserRepository;
import com.study.oauth2.security.OAuth2Attribute;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
		
		OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
		
		System.out.println(oAuth2User);
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
		OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());
		
		Map<String, Object> attributes = oAuth2Attribute.convertToMap();
//		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "email");
	}
	
	public int oAuth2Register(OAuth2RegisterReqDto oAuth2RegisterReqDto) {
		User userEntity = oAuth2RegisterReqDto.toEntity();
		
		userRepository.saveUser(userEntity);
		return userRepository.saveAuthority(
				Authority.builder()
					.userId(userEntity.getUserId())
					.roleId(1)
					.build()
		);
	}
	
	public boolean checkPassword(String email, String password) {
		User userEntity = userRepository.findUserByEmail(email);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(password, userEntity.getPassword());
	}
	
	public int oAuth2ProviderMerge(OAuth2ProviderMergeReqDto oAuth2ProviderMergeReqDto) {
		User userEntity = userRepository.findUserByEmail(oAuth2ProviderMergeReqDto.getEmail());
		
		String provider = oAuth2ProviderMergeReqDto.getProvider();
		
		if(StringUtils.hasText(userEntity.getProvider())) {
			userEntity.setProvider(userEntity.getProvider() + "," + provider);
		}else {
			userEntity.setProvider(provider);
		}
		
		return userRepository.updateProvider(userEntity);
	}
}








