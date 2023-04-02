package com.finalproject.StayFinderApi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.entity.RoomType;
import com.finalproject.StayFinderApi.exception.AppException;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.RoomTypeRepository;
import com.finalproject.StayFinderApi.service.IRoomTypeService;

@Service
public class RoomtypeServiceImpl implements IRoomTypeService{

	@Autowired
	private RoomTypeRepository roomTypeRepo;
	@Override
	public RoomType saveRoomType(RoomType roomType) {
		return roomTypeRepo.save(roomType);
	}

	@Override
	public RoomType updateRoomType(RoomType roomType) {
		return roomTypeRepo.save(roomType);
	}

	@Override
	public void deleteRoomType(Long id) {
		try {
			roomTypeRepo.deleteById(id);
		} catch (Exception e) {
			throw new AppException("That bai, id: " + id + " khong ton tai");
		}
	}

	@Override
	public List<RoomType> getAllRoomType() {
		
		return roomTypeRepo.findAll();
	}

	@Override
	public RoomType getOneRoomType(Long id) {
		Optional<RoomType> optional = roomTypeRepo.findById(id);
		if( optional.isPresent())
			return optional.get();
		else 
			throw new NotFoundException("Roomtype id: "+id + " khong ton tai");
	}

}
