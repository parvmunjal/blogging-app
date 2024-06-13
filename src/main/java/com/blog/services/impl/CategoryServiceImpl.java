package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CategoryRepository;
import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = dtoToCategory(categoryDto);
		Category savedCategory = categoryRepository.save(category);
		return categoryToDto(savedCategory);
	}
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = categoryRepository.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category savedCategory = categoryRepository.save(category);
		return categoryToDto(savedCategory);
	}
	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId).
		orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		categoryRepository.delete(category);
	}
	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId).
		orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		return categoryToDto(category);
	}
	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map(category->categoryToDto(category)).collect(Collectors.toList());
		return categoryDtos;
	}
	
	
	//category to dto
	public CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = modelMapper.map(category,CategoryDto.class);
		return categoryDto;
	}
	//dto to category
	public Category dtoToCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	
}
