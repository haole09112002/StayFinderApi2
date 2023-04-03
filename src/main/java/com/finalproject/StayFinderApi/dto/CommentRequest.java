package com.finalproject.StayFinderApi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

	private long postId;
	
	private String username;
	
	private String content;

	private String imageUrl;
}
