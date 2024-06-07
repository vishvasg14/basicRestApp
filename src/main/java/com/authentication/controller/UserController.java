package com.authentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.ProfileResponseDto;
import com.authentication.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name = "auth")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ProfileResponseDto getMethodName() throws Exception{
		ProfileResponseDto profile = userService.getProfile();
		return profile;
	}
	
	@GetMapping("/search/{param}")
	public List<ProfileResponseDto> searchProfile(@PathVariable String param) throws Exception{
		List<ProfileResponseDto> result = userService.searchProfile(param);
		return result;
	}
	
	@DeleteMapping("/removeprofile")
	public String removeProfile() throws Exception{
		String message = userService.removeProfile();
		return message;
	}
}
