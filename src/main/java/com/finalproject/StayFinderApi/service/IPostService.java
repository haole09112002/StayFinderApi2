package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.PostResponse;

public interface IPostService {
	
	List<PostResponse> getAll();
	
	PostResponse getById(long id);
	
	List<PostResponse> findByAccountUsernameAndStatus(String username, int status);
	
	List<PostResponse> findByStatus(int status);
	
	boolean changeStatus(long id, int status);
	
	List<PostResponse> findByAccountUsername(String username);
	
	
}
