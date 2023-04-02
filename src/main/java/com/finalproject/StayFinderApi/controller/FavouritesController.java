package com.finalproject.StayFinderApi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.finalproject.StayFinderApi.dto.PagedResponse;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.service.IFavouritesAccountPostService;
import com.finalproject.StayFinderApi.service.IHostelService;
import com.finalproject.StayFinderApi.utils.AppConstants;



@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

	@Autowired
	private IFavouritesAccountPostService favouritesService;
	
	@Autowired
	private IHostelService hostelService;
	
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
	public PagedResponse<HostelResp> getListPostFavoritesByUsername(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,@RequestParam(required = true) String username) {
		return hostelService.getListHostelFavouriteByUsername(page,size, username);
	}
	
}
