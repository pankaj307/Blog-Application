package com.maverick.blog.services;

import java.util.List;

import com.maverick.blog.payloads.PostDto;
import com.maverick.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// delete
	void deletePost(Integer postId);
	
	// get all posts
//	List<PostDto> getAllposts();
	
	// Added pagination functionality
//	List<PostDto> getAllposts(Integer pageNumber, Integer pageSize);
	
	// Modified pagination response using PostResponse class
	PostResponse getAllposts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
	
	// get post
	PostDto getPostById(Integer postId);
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all post by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);
	

}
