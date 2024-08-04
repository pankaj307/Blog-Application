package com.maverick.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maverick.blog.entities.Category;
import com.maverick.blog.exceptions.ResourceNotFoundException;
import com.maverick.blog.payloads.CategoryDto;
import com.maverick.blog.repositories.CategoryRepo;
import com.maverick.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category catogory = this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory = this.categoryRepo.save(catogory);
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = categoryRepo.save(category);
		
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(category);

	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category catogory = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.modelMapper.map(catogory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> allCategoryDto = categories.stream().map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return allCategoryDto;
	}

}
