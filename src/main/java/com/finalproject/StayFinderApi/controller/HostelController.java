package com.finalproject.StayFinderApi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.dto.HostelRequest;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.dto.PagedResponse;
import com.finalproject.StayFinderApi.service.IHostelService;
import com.finalproject.StayFinderApi.utils.AppConstants;

@RestController
@RequestMapping("api/hostel")
public class HostelController {

	@Autowired
	private IHostelService hostelService;

	@GetMapping
	public PagedResponse<HostelResp> getAll(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) 
	{
		return hostelService.getAllHostel(page, size);
	}

	
	@GetMapping("/{id}")
	public HostelResp getOne(@PathVariable Long id) {
		return hostelService.getHostelByPostId(id);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		hostelService.deleteHostel(id);
	}

	@PutMapping
	public HostelResp update(@RequestBody HostelRequest hostel) {
		return hostelService.updateHostel(hostel);
	}

	@PostMapping
	public HostelResp save(@RequestBody HostelRequest hostelReq) {
		return hostelService.saveHostel(hostelReq);
	}


	@GetMapping("/search")
	public PagedResponse<HostelResp> findByManyOption(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,@RequestParam(required = false, defaultValue = "") String address,
			@RequestParam(required = false, defaultValue = "0.0") double areaMin,
			@RequestParam(required = false, defaultValue = "100.0") double areaMax,
			@RequestParam(required = false, defaultValue = "0.0") double minRent,
			@RequestParam(required = false, defaultValue = "10000000") double maxRent,
			@RequestParam(required = false, defaultValue = "10") int capacity,
			@RequestParam(required = false, defaultValue = "0") long idRoomType) {

		return hostelService.findByManyOption(page, size,address, areaMin, areaMax, minRent, maxRent, capacity, idRoomType);
	}
	
	@PutMapping("status/{id}")
	public HostelResp updateStatusHostel(@PathVariable long id, @RequestParam(required = false, defaultValue = "0") int status) {
		return hostelService.updateStatusHostel(id, status);
	}
	@GetMapping("available")
	public PagedResponse<HostelResp> findHostelByStatusAndPostStatus(@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size, @RequestParam int hostelStatus, @RequestParam int postStatus)
	
	{
		return hostelService.getHostelByHostelStatusAndPostStatus(page, size, hostelStatus, postStatus);
	}
}
