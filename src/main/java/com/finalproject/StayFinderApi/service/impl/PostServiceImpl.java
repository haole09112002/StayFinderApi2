package com.finalproject.StayFinderApi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.dto.PostResp;
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
	public List<PostResp> getAll() {
		return postRepo.findAll().stream().map(p -> {
			PostResp postResp = new PostResp(p.getId(), new AccountRespone(p.getAccount().getUsername(), p.getAccount().getName(), p.getAccount().getAvatarUrl()), p.getTitle(),
					p.getContent(), p.getNumberOfFavourites(), p.getStatus(), p.getPostTime(), p.getHostel().getId());
			return postResp;
		}).collect(Collectors.toList());
	}

	@Override
	public Post getById(long id) {
		Optional<Post> optional = postRepo.findById(id);
		if (optional.isPresent())
			return optional.get();
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
	public Post addPost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> findByAccountUsernameAndStatus(String username, int status) {

		return postRepo.findByAccountUsernameAndStatus(username, status);
	}

	@Override
	public List<Post> findByStatus(int status) {
		if(status == PostStatusEnum.APPROVED.getValue() || status == PostStatusEnum.NOT_APPROVED.getValue() ||status == PostStatusEnum.NOT_YET_APPROVED.getValue())
			return  postRepo.findByStatus(status);
		throw new BadRequestException("Status id: " + status + " khong ton tai");
	}

	@Override
	public boolean changeStatus(long id, int status) {
		Optional<Post> optional = postRepo.findById(id);
		if (optional.isPresent()) {
			Post p = optional.get();
			if(status == PostStatusEnum.APPROVED.getValue() || status == PostStatusEnum.NOT_APPROVED.getValue() ||status == PostStatusEnum.NOT_YET_APPROVED.getValue())
			{
				p.setStatus(status);
				return true;
			}
			throw new BadRequestException("Status id: " + status + " khong ton tai");
		}
		throw new AppException("That bai, id: "+ id + " khong ton tai");
	}

}
