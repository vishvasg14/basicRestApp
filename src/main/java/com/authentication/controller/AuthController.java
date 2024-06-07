package com.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.LoginRequestDto;
import com.authentication.dto.LoginResponseDto;
import com.authentication.dto.RequestDto;
import com.authentication.dto.ResponseDto;
import com.authentication.entity.Role;
import com.authentication.service.AuthService;


@RestController
@RequestMapping("/authentication")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/registration")
	public ResponseDto createProfile(@RequestBody RequestDto requestDto) throws Exception {
		ResponseDto user = authService.addUserProfile(requestDto, Role.ROLE_USER);
		return user;
	}
	
	@PostMapping("/admin-registration")
	public ResponseDto createAdminProfile(@RequestBody RequestDto requestDto) throws Exception {
		ResponseDto user = authService.addUserProfile(requestDto, Role.ROLE_ADMIN);
		return user;
	}
	
	@PostMapping("/login")
	public LoginResponseDto loginProfile(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
		LoginResponseDto user = authService.loginProfile(loginRequestDto);
		return user;
	}
	
	@PostMapping("/logout")
	public String profileLogout(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
		String message = authService.logoutProfile(loginRequestDto.getEmail());
		return message;
	}
	
}
