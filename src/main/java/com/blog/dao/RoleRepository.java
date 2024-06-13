package com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
