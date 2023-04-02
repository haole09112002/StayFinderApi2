package com.finalproject.StayFinderApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountProfile {
	
	private String name;
	
	private String username;
	
	private boolean gender;
	
	private String phonenumber;
	
	private String avatarUrl;
}
