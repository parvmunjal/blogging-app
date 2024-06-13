package com.blog.services;

import java.util.List;

import com.blog.payload.PostResponse;
import com.blog.payload.PostDto;

public interface PostService {
	//create post
	public PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);
	
	//update post
	public PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete post
	public void deletePost(Integer postId);
	
	//get all posts
	public PostResponse getAllPosts(Integer pageSize,Integer pageNumber,String sortBy,String sortWay);
	
	//get post by id
	public PostDto getPostById(Integer postId);
	
	//get post by category
	public List<PostDto> getPostByCategory(Integer categoryId);
	
	//get post by user
	public List<PostDto> getPostByUser(Integer userId);
	
	//get post by searching keyword
	public List<PostDto> getPostBySearch(String keyword);
} 
