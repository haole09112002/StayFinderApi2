package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.CommentRequest;
import com.finalproject.StayFinderApi.dto.CommentResponse;
import com.finalproject.StayFinderApi.security.UserPrincipal;

public interface ICommentService {
	
	List<CommentResponse> getCommentByPostId(long postId);
	
	boolean deleteCommentById(long id,  UserPrincipal userPrincipal);
	
	CommentResponse addComment(CommentRequest commentRequest,  UserPrincipal userPrincipal);
	
}
