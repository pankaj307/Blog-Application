package com.maverick.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maverick.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
