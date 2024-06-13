package com.blog.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.entities.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private int id;
	@NotBlank
	@Size(min=5,max=200,message = "Min 2 and max 200 characters are allowed")
	private String title;
	@NotBlank
	@Size(min=10,message="Min 10 characters should be there in the content")
	private String content;
	private Date postDate;
	private String imageName;
	private CategoryDto categoryDto;
	private UserDto userDto;
	
	private List<CommentDto> comments =new ArrayList<>();
}
