package com.techproed.java_dev_summer_tr;
public class AuthenticationResponse { //this class is to store the token inside it
	private final String jwt;
	
	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	
	public String getJwt() {
		return jwt;
	}
}