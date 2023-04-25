package com.finalproject.StayFinderApi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.dto.PostResp;
import com.finalproject.StayFinderApi.dto.RoomtypeResponse;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.exception.AppException;
import com.finalproject.StayFinderApi.exception.BadRequestException;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.HostelRepository;
import com.finalproject.StayFinderApi.repository.ImageRepository;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.service.IFavouritesAccountPostService;

@Service
public class FavouritesAccountPostImpl implements IFavouritesAccountPostService{

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private HostelRepository hostelRepo;
	
	@Autowired
	private ImageRepository imageRepo;
	
	@Override
	public boolean addFavourites(String username, long postId) {
		Optional<Account> accountOptional = accountRepo.findByUsername(username);
		Optional<Post> postOptional = postRepo.findById(postId);
		if(accountOptional.isPresent() && postOptional.isPresent())
		{
			if(!checkAccountFavouriedPost(username, postId)) {
				Account account = accountOptional.get();
				Post post = postOptional.get();

				
				List<Post> postsFavourites = account.getListFavouritePosts();
				postsFavourites.add(post);
				account.setListFavouritePosts(postsFavourites);
				
				accountRepo.save(account);
				
				List<Account> accountsLike = post.getListAccountLiked();
				accountsLike.add(account);
				post.setListAccountLiked(accountsLike);
				
				post.setNumberOfFavourites(post.getListAccountLiked().size());
				
				postRepo.save(post);
				
				return true;
			}
			throw new BadRequestException("username: " + username +" already favourites Post id: " + postId);
		}
		throw new AppException("username: " + username +" can't favourites Post id: " + postId);
	}

	@Override
	public boolean checkAccountFavouriedPost(String username, long postId) {
		Optional<Post> postOptional = postRepo.findById(postId);
		if( postOptional.isPresent())
		{
		
			Post post = postOptional.get();
			for(Account acc : post.getListAccountLiked()) {
				if(acc.getUsername().toLowerCase().equals(username.toLowerCase())){
					return true;
				}
			}
			return false;
		}
		throw new NotFoundException(" can't find Post by Post id: " + postId);
	}
	

	@Override
	public boolean unFavourites(String username, long postId) {
		if(checkAccountFavouriedPost(username, postId))
		{
			Optional<Account> accountOptional = accountRepo.findByUsername(username);
			Optional<Post> postOptional = postRepo.findById(postId);
			if(accountOptional.isPresent() && postOptional.isPresent())
			{
				Account account = accountOptional.get();
				Post post = postOptional.get();
				
				List<Post> postsFavourites = account.getListFavouritePosts();
				postsFavourites.remove(post);
				account.setListFavouritePosts(postsFavourites);
				
				accountRepo.save(account);
				
				List<Account> accountsLike = post.getListAccountLiked();
				accountsLike.remove(account);
				post.setListAccountLiked(accountsLike);
				
				post.setNumberOfFavourites(post.getListAccountLiked().size());
				
				postRepo.save(post);
				return true;
			}
		}
	
		throw new NotFoundException("username: " + username +" can't Unfavourites Post id: " + postId);
	}

	@Override
	public List<AccountRespone> accountsFavouriesPost(long postId) {
		Optional<Post> postOptional = postRepo.findById(postId);
		if( postOptional.isPresent())
		{
	
			Post post = postOptional.get();
			return post.getListAccountLiked().stream().map(acc -> {
				return new AccountRespone(acc.getUsername(), acc.getName(), acc.getAvatarUrl());
				
			}).collect(Collectors.toList());
		}
		throw new NotFoundException(" can't find Account favourites by Post id: " + postId);
	}

	@Override
	public List<HostelResp> getListHostelFavoritesByUsername(String username) {
//		
		List<HostelResp> hostelResps = new ArrayList<HostelResp>();
		if (!accountRepo.existsByUsername(username))
			new NotFoundException("Username: " + username + " khong ton tai");
		List<Hostel> hostels = hostelRepo.findAll();
		for(Hostel h : hostels) {
			for(Account acc : h.getPost().getListAccountLiked()) {
				if (acc.getUsername().equals(username)) {
					Post post = h.getPost();
					PostResp postResp = new PostResp(post.getId(),
							new AccountRespone(post.getAccount().getUsername(), post.getAccount().getName(),
									post.getAccount().getAvatarUrl()),
							post.getTitle(), post.getContent(), post.getNumberOfFavourites(), post.getStatus(),
							post.getPostTime(), post.getHostel().getId());
					hostelResps.add(new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
							h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
							new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
							h.getElectricPrice(), h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId())));
				}
					
			}
		}
		Collections.sort(hostelResps, new Comparator<HostelResp>() {
			@Override
			public int compare(HostelResp o1, HostelResp o2) {
				return o2.getPost().getPostTime().compareTo(o1.getPost().getPostTime());
			}
		});
		
		return hostelResps;
		
	}
}
