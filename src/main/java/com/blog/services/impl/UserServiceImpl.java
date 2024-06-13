package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.dao.RoleRepository;
import com.blog.dao.UserRepository;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.UserDto;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=userRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User savedUser = userRepository.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=userRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user->userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
	    userRepository.delete(user);
	}

	
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = dtoToUser(userDto);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = roleRepository.findById(2).get();
		user.getRole().add(role);
		User savedUser = userRepository.save(user);
		return userToDto(savedUser);
	}
	
	public User dtoToUser(UserDto userDto) {
		User user=modelMapper.map(userDto, User.class);
				
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	public UserDto userToDto(User user) {
		UserDto userDto=modelMapper.map(user, UserDto.class) ;
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}

	
	
}
