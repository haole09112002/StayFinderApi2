package com.finalproject.StayFinderApi.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.finalproject.StayFinderApi.dto.ImageResponse;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.service.IAccountService;
import com.finalproject.StayFinderApi.service.ICommentService;
import com.finalproject.StayFinderApi.service.IHostelService;
import com.finalproject.StayFinderApi.service.IImageService;
import com.finalproject.StayFinderApi.service.impl.FileStorageService;
import com.finalproject.StayFinderApi.service.impl.HostelServiceImpl;

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
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private ICommentService commentService;

    @PostMapping("/Hostel/uploadFile")
    public ImageResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("hostelId") long hostelId) {
        String fileName = fileStorageService.storeFile(file);
        System.out.println(fileName);
        String imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
       
        return imageService.addImage(imgUrl, fileName, hostelId);
    }
    
//    @PostMapping("/Account/uploadFile")
//    public Account uploadFileAccount(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
//        String fileName = fileStorageService.storeFile(file);
//        String imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//        return accountService.addAvatar(username, imgUrl);
//    }

    @PostMapping("/Hostel/uploadMultipleFiles")
    public List<ImageResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("hostelId") long hostelId) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, hostelId))
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