package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.PostResp;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.service.IPostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private IPostService postService;
	
	@GetMapping
	public List<PostResp> getAll(){
		return postService.getAll();
	}
	
	@GetMapping("/{id}")
	public Post getById(@PathVariable long id){
		return postService.getById(id);
	}
	@GetMapping("/account/{username}")
	public List<Post> getByAccountUsernameAndStatus(@PathVariable String username, @RequestParam(defaultValue = "0", required = true) int status) {
		return postService.findByAccountUsernameAndStatus(username, status);
	}
	
	@GetMapping("/status/{status}")
	public List<Post> getByStatus(@PathVariable  int status) {
		return postService.findByStatus(status);
	}
	
	@PutMapping("/{id}/status/{status}")
	public boolean changePostStatus(@PathVariable long id,@PathVariable  int status) {
		return postService.changeStatus(id,status);
	}
}
