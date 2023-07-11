package com.finalproject.StayFinderApi.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.finalproject.StayFinderApi.service.impl.CustomAuthenticationProviderImpl;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig  {
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.csrf(csrf -> csrf.disable())
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> {
	        	  auth.requestMatchers("/api/account/signin", "/api/account/signup").permitAll();
	        	  auth.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
	              .anyRequest().authenticated();
	        }
	       
	        );
	    
//	    http.authenticationProvider(authProvider());

	    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		
		
		
		
		return http.build();
	}

	@Bean
	public CustomAuthenticationProviderImpl authProvider() {
		CustomAuthenticationProviderImpl authenticationProvider = new CustomAuthenticationProviderImpl();
		return authenticationProvider;
	}
//	  @Bean
//	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//	    return authConfig.getAuthenticationManager();
//	  }



}
