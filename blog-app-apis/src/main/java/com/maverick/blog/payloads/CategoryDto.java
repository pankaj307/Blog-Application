package com.maverick.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4, message="Length of Category Title must be more than 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10, message="Length of Category Description must be more than 10")
	private String categoryDescription;
}
