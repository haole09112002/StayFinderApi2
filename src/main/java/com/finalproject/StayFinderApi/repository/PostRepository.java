package com.finalproject.StayFinderApi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findByTitleContaining(String title);
	
	List<Post> findByPostTimeAfter(Date date);
	
	List<Post> findByAccountUsernameAndStatus(String username, int status) ;
	
	List<Post> findByStatus(int status) ;
	
	List<Post> findByAccountUsername(String username);
	
	
	
}
