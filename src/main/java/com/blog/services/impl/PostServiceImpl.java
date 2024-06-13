package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.dao.CategoryRepository;
import com.blog.dao.PostRepository;
import com.blog.dao.UserRepository;
import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.PostResponse;
import com.blog.payload.PostDto;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
		User user = userRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		Category category = categoryRepository.findById(categoryId).
		orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		Post post = dtoToPost(postDto);
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setCategory(category);
		post.setUser(user);
		post.setPostDate(new Date());
		
		Post savedPost = postRepository.save(post);
		return postToDto(savedPost);
		
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepository.findById(postId).
		orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = postRepository.save(post);
		return postToDto(updatedPost);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post","id",postId));
		
		postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageSize,Integer pageNumber,String sortBy,String sortWay) {
		Sort sort=null;
		if(sortWay.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = postRepository.findAll(p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post)->postToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setFirstPage(pagePost.isFirst());
		postResponse.setLastPage(pagePost.isLast());
		
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post posts = postRepository.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
		return postToDto(posts);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		List<Post> posts = postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post)-> postToDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = userRepository.findById(userId).
		orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		List<Post> posts = postRepository.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->postToDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostBySearch(String keyword) {
		List<Post> posts = postRepository.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->postToDto(post)).collect(Collectors.toList());
		return postDtos;
	}
	
	
	//post to dto
	public PostDto postToDto(Post post) {
		return modelMapper.map(post, PostDto.class);
	}
	//dto to post
	public Post dtoToPost(PostDto postDto) {
		return modelMapper.map(postDto,Post.class);
	}
}
