package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.PostResponse;
import com.finalproject.StayFinderApi.entity.Post;

public interface IPostService {
	
	List<PostResponse> getAll();
	
	PostResponse getById(long id);
	
	PostResponse addPost(Post post);
	
	List<PostResponse> findByAccountUsernameAndStatus(String username, int status);
	
	List<PostResponse> findByStatus(int status);
	
	boolean deletePost(long id);
	
	boolean changeStatus(long id, int status);
	
}
