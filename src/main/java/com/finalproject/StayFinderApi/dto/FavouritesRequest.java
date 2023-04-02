package com.finalproject.StayFinderApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouritesRequest {
	private String username;
	private long postId;
}
