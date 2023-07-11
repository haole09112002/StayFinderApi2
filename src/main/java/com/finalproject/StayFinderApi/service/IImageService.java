package com.finalproject.StayFinderApi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.finalproject.StayFinderApi.dto.ImageResponse;
import com.finalproject.StayFinderApi.entity.Image;
import com.finalproject.StayFinderApi.security.UserPrincipal;

public interface IImageService  {
	
	ImageResponse addImage(String url, String fileName, long hostelId, UserPrincipal userPrincipal);
	
	List<Image> getImagesByHostelId(Long id);
	
	Boolean deleteImageById(Long id);
	
	List<ImageResponse> addImages(List<String> urls, String filename, long hostelId);
	
	String createImgUrl(MultipartFile file);
	
}
