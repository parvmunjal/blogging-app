package com.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepository  extends JpaRepository<Post, Integer>{
	public List<Post> findByUser(User user);
	public List<Post> findByCategory(Category category);
	
	public List<Post> findByTitleContaining(String title);
}
