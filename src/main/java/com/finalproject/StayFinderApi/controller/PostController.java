package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.PostResponse;
import com.finalproject.StayFinderApi.service.IPostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private IPostService postService;
	
	@GetMapping
	public List<PostResponse> getAll(){
		return postService.getAll();
	}
	
	@GetMapping("/{id}")
	public PostResponse getById(@PathVariable long id){
		return postService.getById(id);
	}
	@GetMapping("/account/{username}")
	public List<PostResponse> getByAccountUsernameAndStatus(@PathVariable String username, @RequestParam( required = true) int status) {
		return postService.findByAccountUsernameAndStatus(username, status);
	}
	
	@GetMapping("/account-post/{username}")
	public List<PostResponse> getByAccountUsername(@PathVariable String username) {
		return postService.findByAccountUsername(username);
	}
	
	@GetMapping("/status/{status}")
	public List<PostResponse> getByStatus(@PathVariable  int status) {
		return postService.findByStatus(status);
	}
	
	@PutMapping("/{id}/status/{status}")
	public boolean changePostStatus(@PathVariable long id,@PathVariable  int status) {
		return postService.changeStatus(id,status);
	}
}
