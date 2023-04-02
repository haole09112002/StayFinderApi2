package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.ScheduleRequest;
import com.finalproject.StayFinderApi.dto.ScheduleResponse;
import com.finalproject.StayFinderApi.entity.Schedule;
import com.finalproject.StayFinderApi.service.IScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
	
	@Autowired
	private IScheduleService scheduleService;
	
	@GetMapping("/post/{postId}")
	public List<ScheduleResponse> getSchedulesByPostId(@PathVariable long postId) {
		return scheduleService.getSchedulesByPostId(postId);
	}

	@GetMapping("/account/{username}")
	public List<ScheduleResponse> getSchedulesByRenterId(@PathVariable String username) {
		return scheduleService.getSchedulesByRenterUsername(username);
	}

	@PostMapping
	public ScheduleResponse addSchedule(@RequestBody ScheduleRequest scheduleRequest) {
		return scheduleService.addSchedule(scheduleRequest);
	}
}
