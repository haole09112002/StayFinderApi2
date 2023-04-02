package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.RoomTypeDTO;
import com.finalproject.StayFinderApi.entity.RoomType;
import com.finalproject.StayFinderApi.service.IRoomTypeService;

@RestController
@RequestMapping("api/room-type")
public class RoomTypeController {

	@Autowired
	private IRoomTypeService roomTypeService;
	
	@GetMapping
	public List<RoomType> getAll() {
		return roomTypeService.getAllRoomType();
	}

	@GetMapping("/{id}")
	public RoomType getOne(@PathVariable Long id) {
		
		RoomType hostel = roomTypeService.getOneRoomType(id);
		if (hostel != null)
			return hostel;
		else {
			throw new RuntimeException("RoomType not found for the id "+ id);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		roomTypeService.deleteRoomType(id);
	}
	
	@PutMapping
	public RoomType update(@RequestBody RoomType roomType) {
		
		return roomTypeService.updateRoomType(roomType);
		
	}
	
	@PostMapping
	public RoomType save(@RequestBody RoomType roomType) {
		return roomTypeService.saveRoomType(roomType);
	}
	
}
