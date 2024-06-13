package com.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
	private int categoryId;
	@NotBlank
	@Size(min=2,max=40,message="Min 2 and max 40 characters are allowed")
	private String categoryTitle;
	@NotBlank
	@Size(max=400,message="Max 400 characters are allowed")
	private String categoryDescription;
}
