package com.finalproject.StayFinderApi.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ScheduleRequest implements Serializable{
private static final long serialVersionUID = 1L;
	
	private String renterUsername;

	private String renterName;

	private String renterPhoneNumber;

	private String content;
	
	private Date meetingTime;
	
	private long postId;
}
