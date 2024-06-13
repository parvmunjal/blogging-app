package com.blog.services;

import java.util.List;

import com.blog.payload.CategoryDto;

public interface CategoryService {
	//create category
	public CategoryDto createCategory(CategoryDto categoryDto);
	//update category
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	//delete category
	public void deleteCategory(Integer categoryId);
	//get category by id
	public CategoryDto getCategoryById(Integer categoryId);
	//get all categories 
	public List<CategoryDto> getAllCategories();
}
