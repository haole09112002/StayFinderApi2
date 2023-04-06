package com.finalproject.StayFinderApi.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Serializable {
private static final long serialVersionUID = 1L;
	
	private long id;
	
	AccountRespone account;
	
	private String title;
	
	private String content;
	
	private long numberOfFavourites;
	
	private int status;
	
	private Date postTime;
	
	private HostelResponse hostel;
}