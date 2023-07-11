package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.StayFinderApi.dto.AccountLogin;
import com.finalproject.StayFinderApi.dto.AccountProfile;
import com.finalproject.StayFinderApi.dto.AccountReq;
import com.finalproject.StayFinderApi.dto.JwtAuthenticationResponse;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.security.JwtTokenProvider;
import com.finalproject.StayFinderApi.security.UserPrincipal;
import com.finalproject.StayFinderApi.service.IAccountService;
import com.finalproject.StayFinderApi.service.IImageService;
import com.finalproject.StayFinderApi.service.impl.CustomAuthenticationProviderImpl;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IImageService imageService;
	
	@Autowired
	private	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomAuthenticationProviderImpl customAuthenticationProviderImpl;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<Account> getAll() {
		return accountService.getAllAccount();
	}

	@GetMapping("/disable")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Account> getDistableAccounts() {
		return accountService.getDisableAccount();
	}

	@GetMapping("/enable")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Account> getEnableAccounts() {
		return accountService.getEnableAccount();
	}

	@GetMapping("/{username}")
	public Account getAccountByUsername(@PathVariable String username) {
		return accountService.getAccountByUsername(username);
	}

	@PostMapping("/signup")
	public Account registerUser(@RequestParam(required = true) String username,
			@RequestParam(required = false) String password, @RequestParam(required = false) String name,
			@RequestParam(name = "file", required = false) MultipartFile file) {
		if (file != null) {
			String imgUrl = imageService.createImgUrl(file);
			return accountService.addAccount(new AccountReq(username, password, name, imgUrl));
		}
		return accountService.addAccount(new AccountReq(username, password, name, null));
	}

	@PutMapping
	@PreAuthorize("hasRole('USER')")
	public Account updateAccountProfile(
			@RequestParam(required = false) String name, @RequestParam(required = false) boolean gender,
			@RequestParam(required = false) String phonenumber,
			@RequestParam(name = "file", required = false) MultipartFile file, Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		if (file != null) {
			String imgUrl = imageService.createImgUrl(file);
			return accountService.updateAccountProfile(new AccountProfile(name, userPrincipal.getUsername(), gender, phonenumber, imgUrl));
		}
		return accountService.updateAccountProfile(new AccountProfile(name, userPrincipal.getUsername(), gender, phonenumber, null));

	}

	@DeleteMapping("/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteAccount(@PathVariable String username) {
		accountService.deleteAccount(username);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticatedUser(@RequestBody AccountLogin accountLogin) {
		Authentication authentication = 
				customAuthenticationProviderImpl.authenticate(
				new UsernamePasswordAuthenticationToken(accountLogin.getUsername(),accountLogin.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PutMapping("/enable/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public Boolean enableAccount(@PathVariable String username) {
		return accountService.enableAccount(username);
	}

	@PutMapping("/disable/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public Boolean disableAccount(@PathVariable String username) {
		return accountService.enableAccount(username);
	}

	@PutMapping("/admin/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public Account giveAdmin(@PathVariable String username) {
		return accountService.giveAdmin(username);
	}
}
