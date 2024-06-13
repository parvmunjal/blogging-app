package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CommentRepository;
import com.blog.dao.PostRepository;
import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = postRepository.findById(postId).
		orElseThrow(()-> new ResourceNotFoundException("Post","Id", postId));
		Comment comment = dtoToComment(commentDto);
		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment);
		return commentToDto(savedComment);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).
		orElseThrow(()-> new ResourceNotFoundException("Comment","Id", commentId));
		commentRepository.delete(comment);
	}
	
	//dto to comment
	public Comment dtoToComment(CommentDto commentDto) {
		return modelMapper.map(commentDto, Comment.class);
	}
	//comment to dto
	public CommentDto commentToDto(Comment comment) {
		return modelMapper.map(comment, CommentDto.class);
	}
}
