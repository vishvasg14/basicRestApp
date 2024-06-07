package com.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.LoginRequestDto;
import com.authentication.dto.LoginResponseDto;
import com.authentication.dto.RequestDto;
import com.authentication.dto.ResponseDto;
import com.authentication.entity.Role;
import com.authentication.entity.User;
import com.authentication.exception.CustomException;
import com.authentication.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepository userRepository;

	public ResponseDto addUserProfile(RequestDto requestDto, Role role) throws Exception {
		User existingUser = userRepository.findByEmail(requestDto.getEmail());
		if (existingUser != null) {
			throw new CustomException("User already registered!!");
		}

		User newUser = new User();
		newUser.setEmail(requestDto.getEmail());
		newUser.setName(requestDto.getName());
		newUser.setPassword(requestDto.getPassword());
		newUser.setRole(role);
		newUser.setActive(true);
		userRepository.save(newUser);

		String token = jwtService.generateToken(newUser);
		return new ResponseDto(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getPassword(),
				newUser.isActive(), token);
	}

	public LoginResponseDto loginProfile(LoginRequestDto loginRequestDto) throws Exception {
		User existingUser = userRepository.findByEmail(loginRequestDto.getEmail());
		if (existingUser == null) {
			throw new CustomException("User does not exist!");
		}

		if (!existingUser.getPassword().equals(loginRequestDto.getPassword())) {
			throw new CustomException("Invalid credentials!");
		}

		boolean isLogin = userRepository.isProfileActive(loginRequestDto.getEmail());
		if (!isLogin) {
			userRepository.activateUser(existingUser.getId());
		}

		String token = jwtService.generateToken(existingUser);
		LoginResponseDto loginResponseDto = new LoginResponseDto("Login Successfully", token);

		return loginResponseDto;
	}

	public String logoutProfile(String email) throws Exception {
		User existingUser = userRepository.findByEmail(email);
		userRepository.profileDeactivate(existingUser.getId());
		return "Profile Logout Successfully!!! See you again.";
	}

}
