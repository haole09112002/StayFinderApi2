package com.finalproject.StayFinderApi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.finalproject.StayFinderApi.exception.StayFinderApiException;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.PositionRepository;
import com.finalproject.StayFinderApi.security.UserPrincipal;
import com.finalproject.StayFinderApi.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private PositionRepository positionRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Account addAccount(AccountReq newAccount) {
		if (accountRepo.existsByUsername(newAccount.getUsername())) {
			throw new BadRequestException("Username: " + newAccount.getUsername() + " đã tồn tại trong hệ thống");
		}

		Account account = new Account();
		account.setAvatarUrl(newAccount.getAvatarUrl());
		account.setUsername(newAccount.getUsername());
		account.setPassword(passwordEncoder.encode(newAccount.getPassword()));
		account.setName(newAccount.getName());
		account.setStatus(AccountStatusEnum.ENABLE.getValue());
		Position position = positionRepo.findByPositionName(PositionNameEnum.ROLE_USER)
				.orElseThrow(() -> new AppException("Can't set role"));
		List<Position> positions = new ArrayList<Position>();
		positions.add(position);
		account.setPositions(positions);
		return accountRepo.save(account);
	}

	@Override
	public Account updateAccountProfile(AccountProfile newAccount) {
		Account account = accountRepo.findByUsername(newAccount.getUsername()).orElseThrow(()-> new NotFoundException("Username: " + newAccount.getUsername() + " không tồn tại trong hệ thống" ));
		if(account.getUsername() == newAccount.getUsername()) {
			if(newAccount.getName()!= null)
				account.setName(newAccount.getName());
			if(newAccount.getPhonenumber()!= null)
				account.setPhonenumber(newAccount.getPhonenumber());
			if(newAccount.isGender() != account.isGender())
				account.setGender(newAccount.isGender());
			if(newAccount.getAvatarUrl()!= null)
				account.setAvatarUrl(newAccount.getAvatarUrl());
			return accountRepo.save(account);
		}
		else 
			throw new StayFinderApiException(HttpStatus.UNAUTHORIZED, "You don't have permission to update account infomation");
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
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new NotFoundException("AccountId: " + id + " không tồn tại trong hệ thống");
	}

	@Override
	public Account checkLogin(AccountLogin accountReq) {
		Boolean isNewAccount = accountRepo.existsByUsername(accountReq.getUsername());
		if (isNewAccount) {
			Account account = accountRepo.getAccountByUserName(accountReq.getUsername());
			if (account.getPassword().equals(accountReq.getPassword()))
				return account;
			else {
				throw new NotFoundException("Sai mật khẩu");
			}
		}
		throw new NotFoundException("User không tồn tại trong hệ thống");
	}

	@Override
	public Account getAccountByUsername(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new NotFoundException("Username: " + username + " không tồn tại trong hệ thống");
	}

	@Override
	public boolean checkUsernameAvailability(String username) {

		return accountRepo.existsByUsername(username);

	}

	@Override
	public List<Account> getDisableAccount() {
		return accountRepo.findAll().stream().filter(item -> item.getStatus() == AccountStatusEnum.DISTABLE.getValue())
				.collect(Collectors.toList());
	}

	@Override
	public List<Account> getEnableAccount() {
		return accountRepo.findAll().stream().filter(item -> item.getStatus() == AccountStatusEnum.ENABLE.getValue())
				.collect(Collectors.toList());
	}

	@Override
	public Boolean changePassword(UserPrincipal userPrincipal, String password) {
		Account account = accountRepo.findByUsername(userPrincipal.getUsername()).orElseThrow(()-> new NotFoundException("Username: " + userPrincipal.getUsername() + " không tồn tại trong hệ thống" ));
		if(account.getUsername() == userPrincipal.getUsername()) {
			account.setPassword(password);
			accountRepo.save(account);
			return true;
		}
		else 
			throw new StayFinderApiException(HttpStatus.UNAUTHORIZED, "You don't have permission to change password");
	}

	@Override
	public Boolean enableAccount(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if (optional.isPresent()) {
			Account account = optional.get();
			account.setStatus(AccountStatusEnum.ENABLE.getValue());
			accountRepo.save(account);
			return true;
		}
		throw new NotFoundException("Can't find Account by username " + username);
	}

	@Override
	public Boolean disableAccount(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if (optional.isPresent()) {
			Account account = optional.get();
			account.setStatus(AccountStatusEnum.DISTABLE.getValue());
			accountRepo.save(account);
			return true;
		}
		throw new NotFoundException("Can't find Account by username " + username);
	}

	@Override
	public Account giveAdmin(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if (optional.isPresent()) {
			Account account = optional.get();
			Position positionUser = positionRepo.findByPositionName(PositionNameEnum.ROLE_USER)
					.orElseThrow(() -> new AppException("Can't set role"));
			Position positionAdmin = positionRepo.findByPositionName(PositionNameEnum.ROLE_ADMIN)
					.orElseThrow(() -> new AppException("Can't set role"));
			List<Position> positions = new ArrayList<Position>();
			positions.add(positionUser);
			positions.add(positionAdmin);
			account.setPositions(positions);
			return accountRepo.save(account);
		}
		throw new NotFoundException("Can't find account by username: " + username);
	}

	@Override
	public Account removeAdmin(String username) {
		Optional<Account> optional = accountRepo.findByUsername(username);
		if (optional.isPresent()) {
			Account account = optional.get();
			Position positionUser = positionRepo.findByPositionName(PositionNameEnum.ROLE_USER)
					.orElseThrow(() -> new AppException("Can't set role"));
			List<Position> positions = new ArrayList<Position>();
			positions.add(positionUser);
			account.setPositions(positions);
			return accountRepo.save(account);
		}
		throw new NotFoundException("Can't find account by username: " + username);
	}

	@Override
	public Account addAvatar(UserPrincipal userPrincipal, String avatarUrl) {
		Account account = accountRepo.findByUsername(userPrincipal.getUsername()).orElseThrow(()-> new NotFoundException("Username: " + userPrincipal.getUsername() + " không tồn tại trong hệ thống" ));
		if(account.getUsername() == userPrincipal.getUsername()) {
			account.setAvatarUrl(avatarUrl);
			return accountRepo.save(account);
		}
		else 
			throw new StayFinderApiException(HttpStatus.UNAUTHORIZED, "You don't have permission to add avatar infomation");
	}
}
