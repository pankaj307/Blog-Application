package com.maverick.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.maverick.blog.security.CustomUserDetailService;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // Disable frame options
	        .csrf(csrf -> csrf.disable()) // Disable CSRF
	        .authorizeHttpRequests(authorizeRequests -> 
	            authorizeRequests
	                .anyRequest().authenticated() // Any request must be authenticated
	        )
	        .httpBasic(withDefaults()); // Enable HTTP Basic authentication

        return http.build();
    }

}
