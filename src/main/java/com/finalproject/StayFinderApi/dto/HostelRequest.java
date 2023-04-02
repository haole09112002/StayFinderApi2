package com.finalproject.StayFinderApi.dto;

import java.io.Serializable;
import java.util.List;

import com.finalproject.StayFinderApi.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostelRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String name;
	
	private int capacity;
	
	private double area;
	
	private String address;
	
	private double rentPrice;

	private double depositPrice;
	
	private int status;
	
	private String description;
	
	private long roomTypeId;

	private double electricPrice;

	private double waterPrice;
	
	private PostRequest post;

	private List<Image> images;
}
