package com.maverick.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.maverick.blog.entities.Category;
import com.maverick.blog.entities.Post;
import com.maverick.blog.entities.User;
import com.maverick.blog.exceptions.ResourceNotFoundException;
import com.maverick.blog.payloads.PostDto;
import com.maverick.blog.payloads.PostResponse;
import com.maverick.blog.repositories.CategoryRepo;
import com.maverick.blog.repositories.PostRepo;
import com.maverick.blog.repositories.UserRepo;
import com.maverick.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		Category catogory = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Categiry Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(catogory);
		
		Post createdpost =  this.postRepo.save(post);
		
		return this.modelMapper.map(createdpost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post upadtedPost = this.postRepo.save(post);
		return this.modelMapper.map(upadtedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepo.delete(post);

	}

//	@Override
//	public List<PostDto> getAllposts(Integer pageNumber, Integer pageSize) {
//		List<Post> allPosts = this.postRepo.findAll();
//		List<PostDto> fetchedPosts = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		return fetchedPosts;

	// Added pagination functionality
//	@Override
//	public List<PostDto> getAllposts(Integer pageNumber, Integer pageSize) {	
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<Post> pagePost = this.postRepo.findAll(pageable);
//		List<Post> allPosts =  pagePost.getContent();
//		List<PostDto> fetchedPosts = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		return fetchedPosts;
//		
//	}
	
	// Modified pagination response using PostResponse class
	@Override
	public PostResponse getAllposts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {	
//		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		
//		Sort sort = null;
//		if (sortDirection.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else {
//			sort = Sort.by(sortBy).descending();
//		}
		
		Sort sort = (sortDirection.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> allPosts =  pagePost.getContent();
		List<PostDto> fetchedPosts = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(fetchedPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));	
		List<Post> posts =  this.postRepo.findByCategory(category);
		List<PostDto> fetchedPosts = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return fetchedPosts;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> fetchedPosts = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return fetchedPosts;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
//		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
