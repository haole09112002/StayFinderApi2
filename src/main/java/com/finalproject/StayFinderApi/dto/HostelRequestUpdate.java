package com.finalproject.StayFinderApi.dto;

import java.util.List;

import com.finalproject.StayFinderApi.entity.Image;

public class HostelRequestUpdate {
private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String name;
	
	private int capacity;
	
	private double area;
	
	private String address;
	
	private double rentPrice;

	private double depositPrice;
	
	private String description;
	
	private long roomTypeId;

	private double electricPrice;

	private double waterPrice;

	private List<Image> images;
}
