package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.finalproject.StayFinderApi.dto.AccountLogin;
import com.finalproject.StayFinderApi.dto.AccountProfile;
import com.finalproject.StayFinderApi.dto.AccountReq;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.service.IAccountService;
import com.finalproject.StayFinderApi.service.impl.FileStorageService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private IAccountService accountService;
	
    @Autowired
    private FileStorageService fileStorageService;
	
	@GetMapping
	public List<Account> getAll(){
		return accountService.getAllAccount();
	}
	
	@GetMapping("/disable")
	public List<Account> getDistableAccounts(){
		return accountService.getDisableAccount();
	}
	
	@GetMapping("/enable")
	public List<Account> getEnableAccounts(){
		return accountService.getEnableAccount();
	}
	
	@GetMapping("/{username}")
	public Account getAccountByUsername(@PathVariable String username){
		return accountService.getAccountByUsername(username);
	}
	
	@PostMapping
	public Account addAccount(@RequestBody AccountReq account) {
		return accountService.addAccount(account);
	}
	
	@PutMapping
	public Account updateAccountProfile(@RequestParam(required = true) String username,@RequestParam(required = false) String name,@RequestParam(required = false) boolean gender,@RequestParam(required = false) String phonenumber, @RequestParam(name = "file", required = false) MultipartFile file) {
	    if(file != null) {
	    	String fileName = fileStorageService.storeFile(file);
	        String imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(fileName)
	                .toUriString();
	        return accountService.updateAccountProfile(new AccountProfile(name, username, gender, phonenumber, imgUrl));
	    }
	    return accountService.updateAccountProfile(new AccountProfile(name, username, gender, phonenumber, null));
		
	}
	
	@DeleteMapping("/{username}")
	public void deleteAccount(@PathVariable String username ) {
		 accountService.deleteAccount(username);
	}
	
	@PostMapping("/login")
	public Account login(@RequestBody AccountLogin accountLogin) {
		return accountService.checkLogin(accountLogin);
	}
	
	@PutMapping("/enable/{username}")
	public Boolean enableAccount(@PathVariable String username) {
		return accountService.enableAccount(username);
	}
	@PutMapping("/disable/{username}")
	public Boolean disableAccount(@PathVariable String username) {
		return accountService.enableAccount(username);
	}
	
	@PutMapping("/admin/{username}")
	public Account giveAdmin(@PathVariable String username) {
		return accountService.giveAdmin(username);
	}
}
