package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.dto.ScheduleRequest;
import com.finalproject.StayFinderApi.dto.ScheduleResponse;

public interface IScheduleService {
	
	
	List<ScheduleResponse> getSchedulesByPostId(long postId);
	
	List<ScheduleResponse> getSchedulesByRenterUsername(String username);
	
	ScheduleResponse addSchedule(ScheduleRequest scheduleRequest);
	
	List<ScheduleResponse> getByPostAccountUsername(String username);
}
