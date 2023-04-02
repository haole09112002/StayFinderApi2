package com.finalproject.StayFinderApi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.StayFinderApi.entity.Image;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.entity.RoomType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HostelResp implements Serializable {
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
	
//	@JsonIgnore
	private RoomtypeResponse roomtype;

	private double electricPrice;

	private double waterPrice;
	
//	@JsonIgnore
	private PostResp post;

	private List<Image> images;
	
	public List<Image> getImages(){
		return this.images == null ? null : new ArrayList<Image>(this.images);
	}

	public void setImages(List<Image> images)
	{
		if (images == null)
			this.images = null;
		else if (images != this.images) {
			this.images = images;
		}
	}
}
