package com.finalproject.StayFinderApi.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.dto.HostelResponse;
import com.finalproject.StayFinderApi.dto.PostResponse;
import com.finalproject.StayFinderApi.dto.RoomtypeResponse;
import com.finalproject.StayFinderApi.dto.ScheduleRequest;
import com.finalproject.StayFinderApi.dto.ScheduleResponse;
import com.finalproject.StayFinderApi.entity.Hostel;
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
					s.getRenterPhoneNumber(), s.getContent(), s.getMeetingTime(), convertToPostResp(s.getPost()) );
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
						s.getRenterPhoneNumber(), s.getContent(), s.getMeetingTime(), convertToPostResp(s.getPost()));
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
						s.getRenterPhoneNumber(), s.getContent(), s.getMeetingTime(), convertToPostResp(s.getPost()));
		}
		throw new AppException("Can't add Schedule, can't find Post by post id: " + scheduleRequest.getPostId());
	}
	@Override
	public List<ScheduleResponse> getByPostAccountUsername(String username){
		if (username != null) {
			List<Schedule> schedules = scheduleRepo.findByPostAccountUsername(username);
			Collections.sort(schedules, new Comparator<Schedule>() {
				@Override
				public int compare(Schedule o1, Schedule o2) {
					return o2.getMeetingTime().compareTo(o1.getMeetingTime());
				}
			});
			return schedules.stream().map(s -> {
				return new ScheduleResponse(s.getId(), s.getRenterUsername(), s.getRenterName(),
						s.getRenterPhoneNumber(), s.getContent(), s.getMeetingTime(),convertToPostResp(s.getPost()));
			}).collect(Collectors.toList());
		}
		throw new NotFoundException("username: " + username + " khong ton tai");
	}

	public PostResponse convertToPostResp(Post p) {
		Hostel h = p.getHostel();
		HostelResponse hostelResponse = new HostelResponse(h.getId(), h.getName(), h.getCapacity(), h.getArea(),
				h.getAddress(), h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
				new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()), h.getElectricPrice(),
				h.getWaterPrice(), h.getImages());
		PostResponse postResp = new PostResponse(p.getId(),
				new AccountRespone(p.getAccount().getUsername(), p.getAccount().getName(),
						p.getAccount().getAvatarUrl()),
				p.getTitle(), p.getContent(), p.getNumberOfFavourites(), p.getStatus(), p.getPostTime(),
				hostelResponse);
		return postResp;
	}
}
