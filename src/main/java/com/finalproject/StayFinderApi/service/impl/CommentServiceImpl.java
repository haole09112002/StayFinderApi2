package com.finalproject.StayFinderApi.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.dto.CommentRequest;
import com.finalproject.StayFinderApi.dto.CommentResponse;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.Comment;
import com.finalproject.StayFinderApi.entity.PositionNameEnum;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.exception.AppException;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.exception.StayFinderApiException;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.CommentRepository;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.security.UserPrincipal;
import com.finalproject.StayFinderApi.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private PostRepository postRepo;

	@Override
	public List<CommentResponse> getCommentByPostId(long postId) {
		List<Comment> comments = commentRepo.findByPostId(postId);
		Collections.sort(comments, new Comparator<Comment>() {
			@Override
			public int compare(Comment o1, Comment o2) {
				return o2.getCommentTime().compareTo(o1.getCommentTime());
			}
		});
		List<CommentResponse> commentResponses = comments.stream().map(i -> {
			AccountRespone accountRespone = new AccountRespone(i.getAccount().getUsername(), i.getAccount().getName(),
					i.getAccount().getAvatarUrl());

			CommentResponse commentResp = new CommentResponse(i.getId(), i.getPost().getId(), accountRespone,
					i.getContent(), i.getCommentTime(), i.getImageUrl());
			return commentResp;
		}).collect(Collectors.toList());

		return commentResponses;
	}

	@Override
	public boolean deleteCommentById(long id, UserPrincipal userPrincipal) {
		Comment comment = commentRepo.findById(id).orElseThrow(()-> new NotFoundException("Comment id không tồn tại"));
		if(comment.getAccount().getId() == userPrincipal.getId() || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(PositionNameEnum.ROLE_ADMIN.toString())) ) {
			commentRepo.deleteById(id);
			return true;
		}
		else 
			throw new StayFinderApiException(HttpStatus.UNAUTHORIZED, "You don't have permission to delete this comment");
		
		
	}

	@Override
	public CommentResponse addComment(CommentRequest commentRequest, UserPrincipal userPrincipal) {
		Optional<Post> postOptional = postRepo.findById(commentRequest.getPostId());
		Optional<Account> accountOptional = accountRepo.findByUsername(userPrincipal.getUsername());
		if(postOptional.isPresent() && accountOptional.isPresent())
		{
			String imgUrl = commentRequest.getImageUrl() == null ? null : commentRequest.getImageUrl();
			Comment comment = commentRepo.save(new Comment(0, postOptional.get(), accountOptional.get(), commentRequest.getContent(), new Date(), imgUrl));
			
			return new CommentResponse(comment.getId(), commentRequest.getPostId(),new AccountRespone(accountOptional.get().getUsername(), accountOptional.get().getName(), accountOptional.get().getAvatarUrl()) , comment.getContent(), comment.getCommentTime(), comment.getImageUrl());
		}
		else 
			throw new AppException("Sai postId hoặc accountId");
	}

}
