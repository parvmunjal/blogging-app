package com.blog.payload;

import java.util.ArrayList;
import java.util.List;

import com.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//data transfer object
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotBlank
	@Size(min=2,max=40,message="Min 2 and max 40 characters are allowed")
	private String name;
	@Email(message = "Email address is not valid")
	private String email;
	private String about;
	@NotEmpty
	@Size(min=5, max=12,message="Password must be of min 5 and max 12 characters")
	private String password;
    private List<RoleDto> role= new ArrayList<>();
}
