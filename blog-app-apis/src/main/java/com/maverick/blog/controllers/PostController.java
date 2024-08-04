package com.maverick.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maverick.blog.config.AppConstants;
import com.maverick.blog.payloads.ApiResponse;
import com.maverick.blog.payloads.PostDto;
import com.maverick.blog.payloads.PostResponse;
import com.maverick.blog.services.FileService;
import com.maverick.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image:images/}")
	private String path;
	

	// create post
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createpost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	//get all posts by user id
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return ResponseEntity.ok(posts);
		
	}
	
	//get all posts by category id
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCatogory(@PathVariable Integer categoryId) {
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(posts);
		
	}
	
	//get all posts
	
//	@GetMapping("/posts")
//	public ResponseEntity<List<PostDto>> getAllPosts(
//			@RequestParam(value="pageNumber", defaultValue="0", required=false) Integer pageNumber,
//			@RequestParam(value="pageSize", defaultValue="10", required=false) Integer pageSize
//			) {
//		List<PostDto> allPosts = this.postService.getAllposts(pageNumber, pageSize);
//		return ResponseEntity.ok(allPosts);
//	}
	
	//get all posts
	// Modified pagination response using PostResponse class
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber", defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue=AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDirection", defaultValue=AppConstants.SORT_DIRECTION, required=false) String sortDirection
			) {
		PostResponse postResponse = this.postService.getAllposts(pageNumber, pageSize, sortBy, sortDirection);
		return ResponseEntity.ok(postResponse);
	}
	
	//get post by id
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto post = this.postService.getPostById(postId);
		return ResponseEntity.ok(post);
	}
	
	// delete post
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is successfully deleted", true), HttpStatus.OK);
	}
	
	// update post
	
	@PutMapping("posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	// search post
	
	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keywords) {
		List<PostDto> searchedPosts = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchedPosts, HttpStatus.OK);
	}
	
	// post image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
		
		PostDto postDto = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	// method to serve image
	
	@GetMapping(value="/post/image/{imageName}", produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}








