package com.finalproject.StayFinderApi.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.StayFinderApi.dto.ImageResponse;
import com.finalproject.StayFinderApi.security.UserPrincipal;
import com.finalproject.StayFinderApi.service.IImageService;
import com.finalproject.StayFinderApi.service.impl.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private IImageService imageService;
    

    @PostMapping("/Hostel/uploadFile")
	@PreAuthorize("hasRole('USER')")
    public ImageResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("hostelId") long hostelId, Authentication authentication) {
    	String fileName = fileStorageService.storeFile(file);
    	String imgUrl = imageService.createImgUrl(file);
    	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return imageService.addImage(imgUrl, fileName, hostelId, userPrincipal);
    }
    

    @PostMapping("/Hostel/uploadMultipleFiles")
	@PreAuthorize("hasRole('USER')")
    public List<ImageResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("hostelId") long hostelId, Authentication authentication) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, hostelId, authentication))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}