package com.finalproject.StayFinderApi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.security.UserPrincipal;
import com.finalproject.StayFinderApi.service.CustomUserDetailsService;

import jakarta.transaction.Transactional;

@Service
public class CustomUserdetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Username không tồn tại"));
		return new UserPrincipal().create(account);
	}

	@Override
	public UserDetails loadUserById(Long id) {
		Account account = accountRepository.findById(id).orElseThrow(()-> new NotFoundException("Username không tồn tại"));
		return  new UserPrincipal().create(account);
	}
}
