package com.authentication.dto;

public class ResponseDto {

	private int id;
	private String email;
	private String name;
	private String password;
	private boolean isActive;
	private String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ResponseDto(int id, String email, String name, String password, boolean isActive, String token) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.isActive = isActive;
		this.token = token;
	}
	
	public ResponseDto(int id, String email, String name, String password, String token) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.token = token;
	}


	public ResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
