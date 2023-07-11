package com.finalproject.StayFinderApi.security;

import java.io.IOException;
import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.finalproject.StayFinderApi.service.CustomUserDetailsService;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter  extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
    private JwtTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jwt = getJwtFromRequest(request);
		try {
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
	            // Lấy id user từ chuỗi jwt
	            String userId = tokenProvider.getUserIdFromJWT(jwt);
	            // Lấy thông tin người dùng từ id
	            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
	            if(userDetails != null) {
	                // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
	                UsernamePasswordAuthenticationToken
	                        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
			}
		} catch (Exception e) {
			LOGGER.error("Could not set user authentication in security context", e);
		}

		 filterChain.doFilter(request, response);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}


}
