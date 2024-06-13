package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.Comment;
import com.blog.payload.ApiResponse;
import com.blog.payload.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	//create comment on post
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer postId){
		CommentDto createdComment = commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);
	}
	//delete comment by commentId
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> createComment(@PathVariable Integer commentId){
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Successfully Deleted", true), HttpStatus.OK);
	}
}
