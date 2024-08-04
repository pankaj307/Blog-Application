package com.maverick.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
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
