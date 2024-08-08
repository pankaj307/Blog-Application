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
		System.out.println("[Hardcoded-INFO]: encoded value for '1234' is - " + password);
		User user = this.userRepo.findById(100).orElseThrow(() -> new ResourceNotFoundException("User", "User ID", 100));
		user.setPassword(password);
		this.userRepo.save(user);
		System.out.println("[Hardcoded-INFO]: Updated user with id: 100, to have the access with basic authentication.");
		
		User user2 = this.userRepo.findById(200).orElseThrow(() -> new ResourceNotFoundException("User", "User ID", 100));
		user2.setPassword(password);
		this.userRepo.save(user2);
	}

}
