package com.finalproject.StayFinderApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRespone {
	private String username;
	
	private String name;
	
	private String avatarUrl;
}
