package com.finalproject.StayFinderApi.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.dto.HostelResponse;
import com.finalproject.StayFinderApi.dto.PostResponse;
import com.finalproject.StayFinderApi.dto.RoomtypeResponse;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.entity.PostStatusEnum;
import com.finalproject.StayFinderApi.exception.AppException;
import com.finalproject.StayFinderApi.exception.BadRequestException;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.service.IPostService;

@Repository
public class PostServiceImpl implements IPostService {

	@Autowired
	private PostRepository postRepo;

	@Override
	public List<PostResponse> getAll() {
		List<PostResponse> postResponses = postRepo.findAll().stream().map(p -> {
			return convertToPostResp(p);
		}).collect(Collectors.toList());
		Collections.sort(postResponses, new Comparator<PostResponse>() {
			public int compare(PostResponse o1, PostResponse o2) {
				return o2.getPostTime().compareTo(o1.getPostTime()) ;
			}
		});
		return postResponses;
	}

	@Override
	public PostResponse getById(long id) {
		Optional<Post> optional = postRepo.findById(id);
		if (optional.isPresent())
			return convertToPostResp(optional.get());
		else {
			throw new NotFoundException("Post id: " + id + " khong ton tai");
		}
	}

	@Override
	public boolean deletePost(long id) {
		postRepo.deleteById(id);
		return true;
	}

	@Override
	public PostResponse addPost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostResponse> findByAccountUsernameAndStatus(String username, int status) {
		List<Post> posts = postRepo.findByAccountUsernameAndStatus(username, status);
		List<PostResponse> postResponses = posts.stream().map(p -> {
			return convertToPostResp(p);
		}).collect(Collectors.toList());
		Collections.sort(postResponses, new Comparator<PostResponse>() {
			public int compare(PostResponse o1, PostResponse o2) {
				return o2.getPostTime().compareTo(o1.getPostTime()) ;
			}
		});
		return postResponses;
	}

	@Override
	public List<PostResponse> findByStatus(int status) {
		if (status == PostStatusEnum.APPROVED.getValue() || status == PostStatusEnum.NOT_APPROVED.getValue()
				|| status == PostStatusEnum.NOT_YET_APPROVED.getValue()) {
			List<Post> posts = postRepo.findByStatus(status);
			return posts.stream().map(p -> {
				return convertToPostResp(p);
			}).collect(Collectors.toList());
		}
		throw new BadRequestException("Status id: " + status + " khong ton tai");
	}

	@Override
	public boolean changeStatus(long id, int status) {
		Optional<Post> optional = postRepo.findById(id);
		if (optional.isPresent()) {
			Post p = optional.get();
			if (status == PostStatusEnum.APPROVED.getValue() || status == PostStatusEnum.NOT_APPROVED.getValue()
					|| status == PostStatusEnum.NOT_YET_APPROVED.getValue()) {
				p.setStatus(status);
				postRepo.save(p);
				return true;
			}
			throw new BadRequestException("Status id: " + status + " khong ton tai");
		}
		throw new AppException("That bai, id: " + id + " khong ton tai");
	}

	public PostResponse convertToPostResp(Post p) {
		Hostel h = p.getHostel();
		HostelResponse hostelResponse = new HostelResponse(h.getId(), h.getName(), h.getCapacity(), h.getArea(),
				h.getAddress(), h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
				new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()), h.getElectricPrice(),
				h.getWaterPrice(), h.getImages());
		PostResponse postResp = new PostResponse(p.getId(),
				new AccountRespone(p.getAccount().getUsername(), p.getAccount().getName(),
						p.getAccount().getAvatarUrl()),
				p.getTitle(), p.getContent(), p.getNumberOfFavourites(), p.getStatus(), p.getPostTime(),
				hostelResponse);
		return postResp;
	}

	@Override
	public List<PostResponse> findByAccountUsername(String username) {
		List<Post> posts = postRepo.findByAccountUsername(username);
		List<PostResponse> postResponses = posts.stream().map(p -> {
			return convertToPostResp(p);
		}).collect(Collectors.toList());
		Collections.sort(postResponses, new Comparator<PostResponse>() {
			public int compare(PostResponse o1, PostResponse o2) {
				return o2.getPostTime().compareTo(o1.getPostTime()) ;
			}
		});
		return postResponses;
	}

}
