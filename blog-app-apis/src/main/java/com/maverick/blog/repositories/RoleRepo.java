package com.maverick.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maverick.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
