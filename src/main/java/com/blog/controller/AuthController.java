package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.LoginException;
import com.blog.payload.JwtAuthRequest;
import com.blog.payload.JwtAuthResponse;
import com.blog.payload.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "AuthController", description = "APIs for authentication")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	private void authenticate(String username,String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		try {
		authenticationManager.authenticate(authenticationToken);
		}
		catch(BadCredentialsException e) {
			System.out.println("Invalid Credentials");
			throw new LoginException("Invalid Credentials");
		}
		}
	
	//user login
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest jwtAuthRequest
			) throws Exception{
		authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		return ResponseEntity.ok(response);
	}
	
	//register new user 
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registeredUser = userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
}
