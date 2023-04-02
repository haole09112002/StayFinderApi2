package com.finalproject.StayFinderApi.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.ScheduleRequest;
import com.finalproject.StayFinderApi.dto.ScheduleResponse;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.entity.Schedule;
import com.finalproject.StayFinderApi.exception.AppException;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.repository.ScheduleRepository;
import com.finalproject.StayFinderApi.service.IScheduleService;

@Service
public class ScheduleServiceImpl implements IScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepo;

	@Autowired
	private PostRepository postRepo;

	@Override
	public List<ScheduleResponse> getSchedulesByPostId(long postId) {
		List<Schedule> schedules = scheduleRepo.findByPostId(postId);
		Collections.sort(schedules, new Comparator<Schedule>() {
			@Override
			public int compare(Schedule o1, Schedule o2) {
				return o2.getMeetingTime().compareTo(o1.getMeetingTime());
			}
		});
		return schedules.stream().map(s -> {
			return new ScheduleResponse(s.getId(), s.getRenterUsername(), s.getRenterName(),
					s.getRenterPhoneNumber(), s.getContent(), s.getMeetingTime(), s.getPost().getId());
		}).collect(Collectors.toList());
	}

	@Override
	public List<ScheduleResponse> getSchedulesByRenterUsername(String username) {
		if (username != null) {
			List<Schedule> schedules = scheduleRepo.findByRenterUsername(username);
			Collections.sort(schedules, new Comparator<Schedule>() {
				@Override
				public int compare(Schedule o1, Schedule o2) {
					return o2.getMeetingTime().compareTo(o1.getMeetingTime());
				}
			});
			return schedules.stream().map(s -> {
				return new ScheduleResponse(s.getId(), s.getRenterUsername(), s.getRenterName(),
						s.getRenterPhoneNumber(), s.getContent(), s.getMeetingTime(), s.getPost().getId());
			}).collect(Collectors.toList());
		}
		throw new NotFoundException("username: " + username + " khong ton tai");
	}

	@Override
	public ScheduleResponse addSchedule(ScheduleRequest scheduleRequest) {
		Optional<Post> optional = postRepo.findById(scheduleRequest.getPostId());
		if (optional.isPresent()) {
			Post post = optional.get();
			Schedule s = scheduleRepo.save(new Schedule(0, scheduleRequest.getRenterUsername(),
					scheduleRequest.getRenterName(), scheduleRequest.getRenterPhoneNumber(),
					scheduleRequest.getContent(), scheduleRequest.getMeetingTime(), post));
			
			return new ScheduleResponse(s.getId(), s.getRenterUsername(), s.getRenterName(),
						s.getRenterPhoneNumber(), s.getContent(), s.getMeetingTime(), s.getPost().getId());
		}
		throw new AppException("Can't add Schedule, can't find Post by post id: " + scheduleRequest.getPostId());
	}

}
