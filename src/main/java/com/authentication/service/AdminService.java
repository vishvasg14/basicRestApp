package com.authentication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.ProfileResponseDto;
import com.authentication.dto.RequestDto;
import com.authentication.entity.User;
import com.authentication.exception.CustomException;
import com.authentication.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private HttpServletRequest httpServletRequest;

	public List<RequestDto> getActiveProfiles() throws Exception {
		List<User> users = userRepository.findByIsActiveTrue();
		List<RequestDto> activeUsers = new ArrayList<>();
		for (User user : users) {
			String token = jwtService.generateToken(user);
			RequestDto responseDto = new RequestDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
					token);
			activeUsers.add(responseDto);
		}

		return activeUsers;
	}

	public ProfileResponseDto getProfile() throws Exception {
		User user = getLoginUser();
		ProfileResponseDto profileResponseDto = new ProfileResponseDto(user.getId(), user.getName(), user.getEmail(),
				user.getPassword());
		return profileResponseDto;
	}

	public User getLoginUser() throws Exception {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");

		String username = null;

		if (authorizationHeader != null) {
			if (authorizationHeader.startsWith("Bearer ")) {
				authorizationHeader = authorizationHeader.substring(7);
			}
			username = jwtService.extractUsername(authorizationHeader);
		}
		if (username != null) {
			User user = userRepository.findByEmail(username);
			if (user != null) {
				return user;
			}
		}
		throw new CustomException("login User not found");
	}

	public String removeProfile() throws Exception {
		User profile = getLoginUser();
		if (userRepository.existsById(profile.getId())) {
			userRepository.deleteById(profile.getId());
			return "Profile removed Successfully.";
		} else {
			throw new CustomException("Profile Not found. Please check for Proper admin Credential!!");
		}
	}
}
