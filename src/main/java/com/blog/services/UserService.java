package com.blog.services;

import java.util.List;

import com.blog.payload.UserDto;

public interface UserService {
	//register user
	public UserDto registerNewUser(UserDto userDto);
	
	//create user
	public UserDto createUser(UserDto userDto);
	
	//update user
	public UserDto updateUser(UserDto userDto,Integer userId);
	
	//get user by id
	public UserDto getUserById(Integer userId);
	
	//get all users
	public List<UserDto> getAllUsers();
	
	//delete user
	public void deleteUser(Integer userId);
	
}
