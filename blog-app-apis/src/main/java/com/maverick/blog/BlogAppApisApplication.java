package com.maverick.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.maverick.blog.config.AppConstants;
import com.maverick.blog.entities.Role;
import com.maverick.blog.entities.User;
import com.maverick.blog.exceptions.ResourceNotFoundException;
import com.maverick.blog.repositories.RoleRepo;
import com.maverick.blog.repositories.UserRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		// This will add ADMIN and NORMAL role in DB if it does not exists in DB
		try {
			Role admin_role = new Role();
			admin_role.setId(AppConstants.ADMIN_USER);
//			admin_role.setName("ADMIN_USER");
			admin_role.setName("ROLE_ADMIN");
			
			Role normal_role = new Role();
			normal_role.setId(AppConstants.NORMAL_USER);
			normal_role.setName("ROLE_NORMAL");
			
			List<Role> roles =  List.of(admin_role, normal_role);
			List<Role> savedRoles = this.roleRepo.saveAll(roles);
			
			savedRoles.forEach(r -> {
				System.out.println("[Hardcoded-INFO]: New Role created - " + r.getName());
			});
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String password = this.passwordEncoder.encode("1234");
		System.out.println("[Hardcoded-INFO]: encoded value for '1234' is - " + password);
		User user = this.userRepo.findById(100).orElseThrow(() -> new ResourceNotFoundException("User", "User ID", 100));
		user.setPassword(password);
		Role role = this.roleRepo.findById(AppConstants.ADMIN_USER).get();
		user.getRoles().add(role);
		this.userRepo.save(user);
		System.out.println("[Hardcoded-INFO]: Updated user with id: 100, to have the access with ADMIN authentication.");
	}

}
