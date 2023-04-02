package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.entity.RoomType;



public interface IRoomTypeService {
	
	public RoomType saveRoomType(RoomType roomType);
	
	public RoomType updateRoomType(RoomType roomType);
	
	public void deleteRoomType(Long id);
	
	public List<RoomType> getAllRoomType();
	
	public RoomType getOneRoomType(Long id);
}
