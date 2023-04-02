package com.finalproject.StayFinderApi.service;

import java.util.List;

import com.finalproject.StayFinderApi.entity.Position;



public interface IPositionService {
	
	public Position savePosition(Position position);
	
	public Position updatePosition(Position position);
	
	public void deletePosition(Long id);
	
	public List<Position> getAllPosition();
	
	public Position getOnePosition(Long id);
	
}
