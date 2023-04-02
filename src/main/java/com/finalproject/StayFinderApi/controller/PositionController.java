package com.finalproject.StayFinderApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.entity.Position;
import com.finalproject.StayFinderApi.service.IPositionService;

@RestController
@RequestMapping("/api/position")
public class PositionController {
	
	@Autowired
	private IPositionService positionService;
	
	@GetMapping
	public List<Position> getAll(){
		return positionService.getAllPosition();
	}
	
//	@PostMapping
//	Position add(@RequestBody String positionName){
//		return positionService.savePosition(new Position(0, positio, null));
//	}
	@GetMapping("/{id}")
	public Position getOneById(@PathVariable long id){
		return positionService.getOnePosition(id);
	}
}
