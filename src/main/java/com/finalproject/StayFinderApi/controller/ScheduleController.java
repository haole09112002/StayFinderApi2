package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.ScheduleRequest;
import com.finalproject.StayFinderApi.dto.ScheduleResponse;
import com.finalproject.StayFinderApi.service.IScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
	
	@Autowired
	private IScheduleService scheduleService;
	
	@GetMapping("/post/{postId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<ScheduleResponse> getSchedulesByPostId(@PathVariable long postId) {
		return scheduleService.getSchedulesByPostId(postId);
	}

	@GetMapping("/account-renter/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<ScheduleResponse> getSchedulesByRenterUsername(@PathVariable String username) {
		return scheduleService.getSchedulesByRenterUsername(username);
	}
	
	@GetMapping("/account/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<ScheduleResponse> getSchedulesByPostAccountUsername(@PathVariable String username) {
		return scheduleService.getByPostAccountUsername(username);
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ScheduleResponse addSchedule(@RequestBody ScheduleRequest scheduleRequest) {
		return scheduleService.addSchedule(scheduleRequest);
	}
}
