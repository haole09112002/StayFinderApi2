package com.finalproject.StayFinderApi.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.service.CustomUserDetailsService;

@Service
public class CustomAuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();

		String password = authentication.getCredentials().toString();
		UserDetails user = userDetailsService.loadUserByUsername(username);
		return checkPassword(user, password);
	}

	private Authentication checkPassword(UserDetails user, String rawPassword) {
		if (passwordEncoder.matches(rawPassword, user.getPassword())) {
			
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
					user.getAuthorities());
		} else {
			throw new BadCredentialsException("Bad credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
