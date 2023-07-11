package com.finalproject.StayFinderApi.service;


import com.finalproject.StayFinderApi.dto.HostelRequest;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.dto.PagedResponse;
import com.finalproject.StayFinderApi.security.UserPrincipal;


public interface IHostelService {
	
	HostelResp saveHostel(HostelRequest hostel, UserPrincipal userPrincipal);
	
	HostelResp updateHostel(HostelRequest hostel, Long id, UserPrincipal userPrincipal);
	
	void deleteHostel(Long id, UserPrincipal userPrincipal);
	
	PagedResponse<HostelResp> getAllHostel(int page, int size);
	
	PagedResponse<HostelResp> findByManyOption(int page, int size,String address, double areaMin, double areMax, double minRent, double maxRent, int capacity, long idRoomType);
	PagedResponse<HostelResp> findByManyOptionAdmin(int page, int size,String address, double areaMin, double areMax, double minRent, double maxRent, int capacity, long idRoomType);
	
	public HostelResp getHostelRespById(Long id);
	
	HostelResp getHostelByPostId(long id);
	
	HostelResp updateStatusHostel(long id, int status, UserPrincipal userPrincipal);
	
	PagedResponse<HostelResp> getHostelByStatus(int page, int size, int status);
	
	PagedResponse<HostelResp> getHostelByHostelStatusAndPostStatus(int page, int size, int status, int postStatus);
		
	
	
}
