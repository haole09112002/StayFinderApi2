package com.finalproject.StayFinderApi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.dto.FavouritesRequest;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.service.IFavouritesAccountPostService;


@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

	@Autowired
	private IFavouritesAccountPostService favouritesService;
	
	@Autowired
	private IFavouritesAccountPostService favouritesAccountPostService;
	
	@PostMapping
	public boolean addFavourites(@RequestBody FavouritesRequest favouritesRequest) {
		return favouritesService.addFavourites(favouritesRequest.getUsername(), favouritesRequest.getPostId());
	}

	@GetMapping("/check")
	public boolean checkAccountFavouriedPost(@RequestParam String username, @RequestParam long postId) {
		return favouritesService.checkAccountFavouriedPost(username, postId);
	}
	

	@PostMapping("/unfavourites")
	public boolean unFavourites(@RequestBody FavouritesRequest favouritesRequest) {
		
		return favouritesService.unFavourites(favouritesRequest.getUsername(), favouritesRequest.getPostId());
	
	}


	@GetMapping("/accounts")
	public List<AccountRespone> getAccountsFavouriesPost(@RequestParam(required = true) long postId) {
		return favouritesService.accountsFavouriesPost(postId);
	}

	@GetMapping("/hostels")
	public List<HostelResp> getListPostFavoritesByUsername(@RequestParam(required = true) String username) {
		return favouritesAccountPostService.getListHostelFavoritesByUsername(username);
	}
	
}
