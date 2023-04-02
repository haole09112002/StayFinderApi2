package com.finalproject.StayFinderApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account>  findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByUsernameAndPassword(String username, String password);
	
	Boolean existsByPassword(String password);
	
	default Account getAccountByUserName(String username) {
		return findByUsername(username)
				.orElseThrow(() -> new RuntimeException("ERROR get Account By Username"));
	}
}
