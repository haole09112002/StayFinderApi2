package com.finalproject.StayFinderApi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.entity.Position;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.PositionRepository;
import com.finalproject.StayFinderApi.service.IPositionService;

@Service
public class PositionServiceImpl implements IPositionService {
	
	@Autowired
	private PositionRepository positionRepo;

	@Override
	public Position savePosition(Position position) {
		
		return positionRepo.save(position);
	}

	@Override
	public Position updatePosition(Position position) {
		return  positionRepo.save(position);
	}

	@Override
	public void deletePosition(Long id) {
		positionRepo.deleteById(id);
		
	}

	@Override
	public List<Position> getAllPosition() {
		
		return positionRepo.findAll();
	}

	@Override
	public Position getOnePosition(Long id) {
		Optional<Position> optional = positionRepo.findById(id);
		if( optional.isPresent())
			return optional.get();
		else 
			throw new NotFoundException("Position Id : " + id + " Khong ton tai");
	}
	
}
