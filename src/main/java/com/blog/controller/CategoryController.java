 package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	//post- category create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategoryDto = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategoryDto,HttpStatus.CREATED);
	}
	//put- category update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCategory);
	}
	//get- category by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
		CategoryDto categoryById = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(categoryById);
	}
	//get- categories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		List<CategoryDto> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
	//delete- category delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("Sucessfully Deleted",true),HttpStatus.OK);
	}
}
