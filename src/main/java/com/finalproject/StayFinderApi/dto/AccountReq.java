package com.finalproject.StayFinderApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AccountReq {
	
	private String username;
	
	private String password;
	
	private String name;
	
	private String avatarUrl;
}
