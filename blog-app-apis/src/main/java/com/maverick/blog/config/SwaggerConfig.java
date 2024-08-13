package com.maverick.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI().info(getInfo())
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                            .type(Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .in(In.HEADER)
                            .name("Authorization")
                    )
                );
	}
	
	private Info getInfo() {
		return new Info()
				.title("Blogging Application")
				.description("This is backend of blogging api project created for learning purpose.")
				.version("1.0")
				.contact(new Contact().name("Maverick").email("pankushwah123@gmail.com"));
	}
	
	private SecurityScheme createApiKeyScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .type(Type.APIKEY)
                .in(In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT");
    }

}
