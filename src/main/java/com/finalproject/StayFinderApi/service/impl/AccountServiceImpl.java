package com.finalproject.StayFinderApi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.AccountLogin;
import com.finalproject.StayFinderApi.dto.AccountProfile;
import com.finalproject.StayFinderApi.dto.AccountReq;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.AccountStatusEnum;
import com.finalproject.StayFinderApi.entity.Position;
import com.finalproject.StayFinderApi.entity.PositionNameEnum;
import com.finalproject.StayFinderApi.exception.AppException;
import com.finalproject.StayFinderApi.exception.BadRequestException;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.PositionRepository;
import com.finalproject.StayFinderApi.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService{

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private PositionRepository positionRepo;

	@Override
	public Account addAccount(AccountReq newAccount) {
		if(accountRepo.existsByUsername(newAccount.getUsername())){
			throw new BadRequestException("Username: "  + newAccount.getUsername()+ " đã tồn tại trong hệ thống");
		}
		
		Account account = new Account();
		account.setAvatarUrl(newAccount.getAvatarUrl());
		account.setUsername(newAccount.getUsername());
		account.setPassword(newAccount.getPassword());
		account.setName(newAccount.getName());
		account.setStatus(AccountStatusEnum.ENABLE.getValue());
		Position position = new Position();
		Optional<Position> optional = positionRepo.findById((long)1);
		if(optional.isPresent())
			position = optional.get();
		else {
			throw new AppException("Account not set!");
		}
		account.setPosition(position);
		return accountRepo.save(account);
	}

	@Override
	public Account updateAccountProfile(AccountProfile newAccount) {
		Optional<Account> optional = accountRepo.findByUsername(newAccount.getUsername());
		if(optional.isPresent())
		{
			Account account = optional.get();
			if(newAccount.getName()!= null)
				account.setName(newAccount.getName());
			if(newAccount.getPhonenumber()!= null)
				account.setPhonenumber(newAccount.getPhonenumber());
			if(newAccount.isGender())
				account.setGender(newAccount.isGender());
			if(newAccount.getAvatarUrl()!= null)
				account.setAvatarUrl(newAccount.getAvatarUrl());
			return accountRepo.save(account);
		}
		else {
			throw new NotFoundException("Username: " + newAccount.getUsername() + " không tồn tại trong hệ thống" );
		}
	}

	@Override
	public void deleteAccount(String username) {
		Account account = accountRepo.getAccountByUserName(username);
		account.setStatus(AccountStatusEnum.DISTABLE.getValue());
		accountRepo.save(account);
	}

	@Override
	public List<Account> getAllAccount() {
		return accountRepo.findAll();
	}
	

	@Override
	public Account getOneAccount(Long id) {
		Optional<Account> optional = accountRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new NotFoundException("AccountId: " + id+ " không tồn tại trong hệ thống");
	}
	
	@Override
	public Account checkLogin(AccountLogin accountReq) {
		Boolean isNewAccount = accountRepo.existsByUsername(accountReq.getUsername());
		if (isNewAccount) {
			Account account = accountRepo.getAccountByUserName(accountReq.getUsername());
			if(account.getPassword().equals(accountReq.getPassword()))
				return account;
			else {
				throw new NotFoundException("Sai mật khẩu" );
			}
		}
		throw new NotFoundException("User không tồn tại trong hệ thống");
	}

	@Override
	public Account getAccountByUsername(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new NotFoundException("Username: " + username + " không tồn tại trong hệ thống" );
	}

	@Override
	public boolean checkUsernameAvailability(String username) {
		
		return accountRepo.existsByUsername(username);
		
	}

	@Override
	public List<Account> getDisableAccount() {
		return accountRepo.findAll()
				.stream()
				.filter(item -> item.getStatus() == AccountStatusEnum.DISTABLE.getValue()).collect(Collectors.toList());
	}

	@Override
	public List<Account> getEnableAccount() {
		return accountRepo.findAll()
				.stream()
				.filter(item -> item.getStatus() == AccountStatusEnum.ENABLE.getValue()).collect(Collectors.toList());
	}

	public Boolean changePassword(String username, String password) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setPassword(password);
			accountRepo.save(account);
			return true;
		}
		else {
			throw new RuntimeException("Can't find account by username: " + username);
		}
	}

	@Override
	public Boolean enableAccount(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setStatus(AccountStatusEnum.ENABLE.getValue());
			accountRepo.save(account);
			return true;
		}
		throw new RuntimeException("Can't find Account by username " + username);
	}
	
	@Override
	public Boolean disableAccount(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setStatus(AccountStatusEnum.DISTABLE.getValue());
			accountRepo.save(account);
			return true;
		}
		throw new RuntimeException("Can't find Account by username " + username);
	}

	@Override
	public Account giveAdmin(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setPosition(positionRepo.findByPositionName(PositionNameEnum.ROLE_ADMIN).get());
			return accountRepo.save(account);
		}
		throw new RuntimeException("Can't find account by username: " + username);
	}

	@Override
	public Account removeAdmin(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setPosition(positionRepo.findByPositionName(PositionNameEnum.ROLE_USER).get());
			return accountRepo.save(account);
		}
		throw new RuntimeException("Can't find account by username: " + username);
	}
	@Override
	public Account addAvatar(String username, String avatarUrl) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if(optional.isPresent()) {
			Account account = optional.get();
			account.setAvatarUrl(avatarUrl);
			return accountRepo.save(account);
		}
		throw new RuntimeException("Can't find account by username: " + username);
	}
}
