package com.finalproject.StayFinderApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.StayFinderApi.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
	List<Schedule> findByPostId(long id);
	
	List<Schedule> findByRenterUsername(String username);
	
	List<Schedule> findByPostAccountUsername(String username);
}
