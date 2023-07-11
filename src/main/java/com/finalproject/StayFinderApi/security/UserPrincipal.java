package com.finalproject.StayFinderApi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.AccountStatusEnum;
public class UserPrincipal implements UserDetails {


	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String fullname;
	
	private String username;
	
	@JsonIgnore
	private String password;
	
	private boolean isAccountNonLocked;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal() {
		
	}
	
	
	public UserPrincipal(Long id, String username, String fullname, String password, boolean isAccountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.password = password;
		this.isAccountNonLocked = isAccountNonLocked;
		this.authorities = authorities;
	}



	public static UserPrincipal create(Account user) {
		
		List<GrantedAuthority> authorities = user.getPositions().stream().map(role -> new SimpleGrantedAuthority(role.getPositionName().toString())).collect(Collectors.toList());
		boolean isAccountNonLocked = false;
		if(user.getStatus() == AccountStatusEnum.ENABLE.getValue()) {
			isAccountNonLocked = true;
		}
		
		return new UserPrincipal(user.getId(), user.getUsername(), user.getName(), user.getPassword(), isAccountNonLocked, authorities);
				
	}

	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities == null ? null : new ArrayList<>(authorities);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}



	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}


	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
