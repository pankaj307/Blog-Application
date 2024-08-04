package com.maverick.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.maverick.blog.entities.User;
import com.maverick.blog.exceptions.ResourceNotFoundException;
import com.maverick.blog.repositories.UserRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		String password = this.passwordEncoder.encode("1234");
		System.out.println(password);
		User user = this.userRepo.findById(100).orElseThrow(() -> new ResourceNotFoundException("User", "User ID", 100));
		user.setPassword(password);
		this.userRepo.save(user);
		
	}

}
