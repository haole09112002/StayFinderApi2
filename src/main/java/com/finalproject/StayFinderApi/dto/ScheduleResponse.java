package com.finalproject.StayFinderApi.dto;

import java.io.Serializable;
import java.util.Date;

import com.finalproject.StayFinderApi.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String username;

	private String renterName;

	private String renterPhoneNumber;

	private String content;
	
	private Date meetingTime;
	
	private long postId;
		
}
