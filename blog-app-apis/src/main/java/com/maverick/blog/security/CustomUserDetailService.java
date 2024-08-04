package com.maverick.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maverick.blog.entities.User;
import com.maverick.blog.exceptions.ResourceNotFoundException;
import com.maverick.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// load user form DB by user-name
		User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email", username));
		
		return user;
	}

}
