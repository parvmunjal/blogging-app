package com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
