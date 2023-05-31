package com.study.oauth2.service;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.study.oauth2.entity.User;
import com.study.oauth2.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final UserRepository userRepository;
	private final JavaMailSender javaMailSender;
	
	public int validAndSendEmail(String email) {
		User userEntity = userRepository.findUserByEmail(email);
		if(userEntity == null) {
			return 2;
		}
		
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			helper.setSubject("스프링부트 비밀번호 찾기 수업");
			helper.setFrom("skjil1218@gmail.com");
			helper.setTo(email);
			String token = UUID.randomUUID().toString().replaceAll("-", "");
			message.setText(
					"<div>"
					+ "<h1>비밀번호 찾기</h1>"
					+ "<p>비밀번호를 변경하려면 아래의 버튼을 클릭하세요.</p>"
					+ "<a href=\"http://localhost:3000/auth/forgot/password/new/" + token + "\">비밀번호 변경하기</a>"
					+ "</div>", "utf-8", "html");
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			return 3;
		} catch (Exception e) {
			e.printStackTrace();
			return 4;
		}
		
		return 1;
	}
}










