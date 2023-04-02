package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.CommentRequest;
import com.finalproject.StayFinderApi.dto.CommentResponse;

public interface ICommentService {
	
	List<CommentResponse> getCommentByPostId(long postId);
	
	boolean deleteCommentById(long id);
	
	CommentResponse addComment(CommentRequest commentRequest);
	
}
