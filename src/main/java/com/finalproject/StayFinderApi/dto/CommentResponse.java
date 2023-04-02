package com.finalproject.StayFinderApi.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

	private long id;

	private long postId;
	
	private AccountRespone account;
	
	private String content;
	
	private Date commentTime;

	private String imageUrl;
}
