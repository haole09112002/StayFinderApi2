package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.AccountLogin;
import com.finalproject.StayFinderApi.dto.AccountProfile;
import com.finalproject.StayFinderApi.dto.AccountReq;
import com.finalproject.StayFinderApi.entity.Account;



public interface IAccountService {
	
	public Account addAccount(AccountReq account);
	
	public Account updateAccountProfile(AccountProfile account);
	
	public void deleteAccount(String username);
	
	public List<Account> getAllAccount();
	
	public Account getOneAccount(Long id);
	
	public Account checkLogin(AccountLogin accountLogin);
	
	public Account getAccountByUsername(String username);
	
	public boolean checkUsernameAvailability(String username);
	
	public List<Account> getDisableAccount();
	
	public List<Account> getEnableAccount();
	
	public Boolean enableAccount(String username);
	
	public Boolean disableAccount(String usename);
	
	public Account giveAdmin(String username);
	
	public Account removeAdmin(String username);
	
	public Account addAvatar(String username, String avatarUrl);
	
}
