package com.finalproject.StayFinderApi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.finalproject.StayFinderApi.dto.ImageResponse;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.entity.Image;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.exception.StayFinderApiException;
import com.finalproject.StayFinderApi.repository.HostelRepository;
import com.finalproject.StayFinderApi.repository.ImageRepository;
import com.finalproject.StayFinderApi.security.UserPrincipal;
import com.finalproject.StayFinderApi.service.IImageService;

@Service
public class ImageServiceImpl implements IImageService {

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private HostelRepository hostelRepository;
	@Autowired
	private FileStorageService fileStorageService;

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
	public ImageResponse addImage(String url, String fileName, long hostelId, UserPrincipal userPrincipal) {

		Hostel hostel = hostelRepository.findById(hostelId)
				.orElseThrow(() -> new NotFoundException("Hostel id: " + hostelId + " khong ton tai"));
		if(hostel.getPost().getAccount().getId() == userPrincipal.getId()){
			
				Image image = imageRepository.save(new Image(0, url, fileName, hostel));
				return new ImageResponse(image.getId(), image.getUrl(), image.getHostel().getId());
		}
		else 
			throw new StayFinderApiException(HttpStatus.UNAUTHORIZED, "You don't have permission to add new image");
	

	}

	@Override
	public String createImgUrl(MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);
        String imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
        		.path("api")
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        return imgUrl;
	}

}
