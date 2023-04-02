package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.finalproject.StayFinderApi.dto.AccountProfile;
import com.finalproject.StayFinderApi.dto.CommentRequest;
import com.finalproject.StayFinderApi.dto.CommentResponse;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.service.ICommentService;
import com.finalproject.StayFinderApi.service.impl.FileStorageService;

@RestController
@RequestMapping("api/comment")
public class CommentController {

	@Autowired
	private ICommentService commentService;
	
    @Autowired
    private FileStorageService fileStorageService;

	@GetMapping("/post/{postId}")
	public List<CommentResponse> getCommentByPostId(@PathVariable long postId) {
		return commentService.getCommentByPostId(postId);
	}

	@DeleteMapping("/{id}")
	public boolean deletePostbyId(@PathVariable long id) {
		return commentService.deleteCommentById(id);
	}

	@PostMapping
	public CommentResponse addComment(@RequestParam(required = true) long postId,@RequestParam(required = true) String username,@RequestParam(required = true) String content, @RequestParam(name = "file", required = false) MultipartFile file ){
		  if(file != null) {
		    	String fileName = fileStorageService.storeFile(file);
		        String imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
		                .path("/downloadFile/")
		                .path(fileName)
		                .toUriString();
		        return commentService.addComment(new CommentRequest(postId, username, content, imgUrl));
		    }
		  return commentService.addComment(new CommentRequest(postId, username, content, null));	
	}
}
