package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.StayFinderApi.dto.CommentRequest;
import com.finalproject.StayFinderApi.dto.CommentResponse;
import com.finalproject.StayFinderApi.security.UserPrincipal;
import com.finalproject.StayFinderApi.service.ICommentService;
import com.finalproject.StayFinderApi.service.IImageService;

@RestController
@RequestMapping("api/comment")
public class CommentController {

	@Autowired
	private ICommentService commentService;
    
    @Autowired 
    private IImageService imageService;

	@GetMapping("/post/{postId}")
	public List<CommentResponse> getCommentByPostId(@PathVariable long postId) {
		return commentService.getCommentByPostId(postId);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public boolean deletePostbyId(@PathVariable long id, Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return commentService.deleteCommentById(id, userPrincipal);
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public CommentResponse addComment(@RequestParam(required = true) long postId,@RequestParam(required = true) String username,@RequestParam(required = true) String content, @RequestParam(name = "file", required = false) MultipartFile file, Authentication authentication ){
		 UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		if(file != null) {
		        String imgUrl = imageService.createImgUrl(file);
		        return commentService.addComment(new CommentRequest(postId, username, content, imgUrl), userPrincipal);
		    }
		  return commentService.addComment(new CommentRequest(postId, username, content, null), userPrincipal);	
	}
}
