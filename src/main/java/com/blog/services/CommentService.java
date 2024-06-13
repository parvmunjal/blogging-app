package com.blog.services;

import com.blog.payload.CommentDto;

public interface CommentService {
	//create comment
	public CommentDto createComment(CommentDto commentDto,Integer postId);
	//delete comment
	public void deleteComment(Integer commentId);
	
}
