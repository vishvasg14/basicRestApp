package com.authentication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.ProfileResponseDto;
import com.authentication.entity.Role;
import com.authentication.entity.User;
import com.authentication.exception.CustomException;
import com.authentication.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HttpServletRequest httpServletRequest;

	public ProfileResponseDto getProfile() throws Exception {
		User user = getLoginUser();
		ProfileResponseDto profileResponseDto = new ProfileResponseDto(user.getId(), user.getName(), user.getEmail(),
				user.getPassword());
		return profileResponseDto;
	}

	public List<ProfileResponseDto> searchProfile(String param) throws Exception {
		User profile = getLoginUser();
		if (profile.getRole() == Role.ROLE_ADMIN) {
			return userRepository.profileSearchForAdmin(param);
		} else if (profile.getRole() == Role.ROLE_USER) {
			return userRepository.profileSearchForUser(param);
		} else {
			throw new CustomException("Searched Result not available for this '"+param+"'!!!");
		}
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
}
