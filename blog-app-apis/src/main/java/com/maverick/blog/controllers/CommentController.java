package com.maverick.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maverick.blog.payloads.ApiResponse;
import com.maverick.blog.payloads.CommentDto;
import com.maverick.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
		CommentDto createdComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("comments/{commentID}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentID) {
		this.commentService.deleteComment(commentID);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is deleted successfully.", true), HttpStatus.OK);
	}
	
}
