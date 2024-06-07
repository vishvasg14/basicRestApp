package com.authentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.ProfileResponseDto;
import com.authentication.dto.RequestDto;
import com.authentication.service.AdminService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name = "auth")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/profile")
	public ProfileResponseDto getMethodName() throws Exception{
		ProfileResponseDto profile = adminService.getProfile();
		return profile;
	}
	
	@GetMapping("/activeprofile")
	public List<RequestDto> getActiveUserProfile() throws Exception{
		List<RequestDto> profiles = adminService.getActiveProfiles();
		return profiles;
	}
	
	@DeleteMapping("/removeprofile")
	public String removeProfile() throws Exception{
		String message = adminService.removeProfile();
		return message;
	}
}
