package com.maverick.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maverick.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
