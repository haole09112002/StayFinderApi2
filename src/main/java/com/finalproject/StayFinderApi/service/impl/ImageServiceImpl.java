package com.finalproject.StayFinderApi.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.ImageResponse;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.entity.Image;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.HostelRepository;
import com.finalproject.StayFinderApi.repository.ImageRepository;
import com.finalproject.StayFinderApi.service.IImageService;

@Service
public class ImageServiceImpl implements IImageService {

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private HostelRepository hostelRepository;

	@Override
	public List<Image> getImagesByHostelId(Long id) {

		return imageRepository.findByHostelId(id);
	}

	@Override
	public Boolean deleteImageById(Long id) {
		imageRepository.deleteById(id);
		return true;
	}

	@Override
	public List<ImageResponse> addImages(List<String> urls, String fileName, long hostelId) {
		List<ImageResponse> imageResponses = new ArrayList<ImageResponse>();
		Hostel hostel = hostelRepository.findById(hostelId)
				.orElseThrow(() -> new NotFoundException("Hostel id: " + hostelId + " khong ton tai"));
		for (String url : urls) {
			Image image = imageRepository.save(new Image(0, url, fileName, hostel));
			imageResponses.add(new ImageResponse(image.getId(), image.getUrl(), image.getHostel().getId()));

		}
		return imageResponses;

	}

	@Override
	public ImageResponse addImage(String url, String fileName, long hostelId) {

		Hostel hostel = hostelRepository.findById(hostelId)
				.orElseThrow(() -> new NotFoundException("Hostel id: " + hostelId + " khong ton tai"));
		Image image = imageRepository.save(new Image(0, url, fileName, hostel));
	
		return new ImageResponse(image.getId(), image.getUrl(), image.getHostel().getId());

	}

}
