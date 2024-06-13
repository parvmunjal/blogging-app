package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payload.ApiResponse;
import com.blog.payload.FileResponse;
import com.blog.payload.PostResponse;
import com.blog.payload.PostDto;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
	@Autowired
	private PostService postService;
	
	
	@Autowired
	private FileService fileService;
	
	//post- create post
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		
		PostDto createdPost = postService.createPost(postDto, categoryId, userId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	//get post by user
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> postDtos = postService.getPostByUser(userId);
		return ResponseEntity.ok(postDtos);
	}
	//get post by category
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> postDtos = postService.getPostByCategory(categoryId);
		return ResponseEntity.ok(postDtos);
	}
	
	//get post by postid
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = postService.getPostById(postId);
		return ResponseEntity.ok(postDto);
	}
	//get all posts
	@GetMapping("/post/")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortWay",defaultValue=AppConstants.SORT_WAY,required=false) String sortWay
			){
			PostResponse posts = postService.getAllPosts(pageSize,pageNumber,sortBy,sortWay);
			return ResponseEntity.ok(posts);
	}
	
	//delete post by id 
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>
		(new ApiResponse("Successfully Deleted",true),HttpStatus.OK);
	}
	//update post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPost = postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPost);
	}
	
	//get post by keyword
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> getPostByKeyword(@PathVariable String keyword){
		List<PostDto> postBySearch = postService.getPostBySearch(keyword);
		return ResponseEntity.ok(postBySearch);
	}
	

	
	String path="src/main/resources/static/images/";
	//upload file
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		
			PostDto postDto = postService.getPostById(postId);
			String fileName = fileService.uploadImage(path, image);
			postDto.setImageName(fileName);
			PostDto updatePost = postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);	
	}
	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        try (InputStream resource = fileService.getResource(path, imageName)) {
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        }
    }
	
}
