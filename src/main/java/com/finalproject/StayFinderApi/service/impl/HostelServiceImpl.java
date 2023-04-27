package com.finalproject.StayFinderApi.service.impl;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.finalproject.StayFinderApi.dto.AccountRespone;
import com.finalproject.StayFinderApi.dto.HostelRequest;
import com.finalproject.StayFinderApi.dto.HostelResp;
import com.finalproject.StayFinderApi.dto.PagedResponse;
import com.finalproject.StayFinderApi.dto.PostResp;
import com.finalproject.StayFinderApi.dto.RoomtypeResponse;
import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.entity.PostStatusEnum;
import com.finalproject.StayFinderApi.entity.RoomType;
import com.finalproject.StayFinderApi.exception.AppException;
import com.finalproject.StayFinderApi.exception.BadRequestException;
import com.finalproject.StayFinderApi.exception.NotFoundException;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.HostelRepository;
import com.finalproject.StayFinderApi.repository.ImageRepository;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.repository.RoomTypeRepository;
import com.finalproject.StayFinderApi.service.IHostelService;
import com.finalproject.StayFinderApi.utils.AppUtils;

@Service
public class HostelServiceImpl implements IHostelService {

	@Autowired
	private HostelRepository hostelRepo;

	@Autowired
	private RoomTypeRepository roomTypeRepo;

	@Autowired
	private ImageRepository imageRepo;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private PostRepository postRepo;

	@Override
	public HostelResp saveHostel(HostelRequest hostelReq) {
		Hostel newHostel = new Hostel(hostelReq.getName(), hostelReq.getCapacity(), hostelReq.getArea(),
				hostelReq.getAddress(), hostelReq.getRentPrice(), hostelReq.getDepositPrice(), hostelReq.getStatus(),
				hostelReq.getDescription(), hostelReq.getElectricPrice(), hostelReq.getWaterPrice());

		RoomType roomType = roomTypeRepo.findById(hostelReq.getRoomTypeId())
				.orElseThrow(() -> new NotFoundException("Không tồn tại roomtypeId"));
		newHostel.setRoomtype(roomType);

		Hostel hostel = hostelRepo.save(newHostel);

//		if (!hostelReq.getImages().isEmpty()) {
//			hostelReq.getImages().forEach(img -> {
//				img.setHostel(hostel);
//				imageRepo.save(img);
//			});
//		}

		Post post = new Post();
		Optional<Account> accountOptional = accountRepo.findById(hostelReq.getPost().getAccountId());
		if (accountOptional.isPresent())
			post.setAccount(accountOptional.get());
		else {
			throw new RuntimeException("Can't find account by id: " + hostelReq.getPost().getAccountId());
		}
		post.setAccount(accountRepo.findById(hostelReq.getPost().getAccountId()).get());
		post.setContent(hostelReq.getPost().getContent());
		post.setStatus(PostStatusEnum.NOT_YET_APPROVED.getValue());
		post.setTitle(hostelReq.getPost().getTitle());
		post.setHostel(hostelRepo.findById(hostel.getId()).get());
		post.setNumberOfFavourites(0);
		post.setPostTime(new Date());
		postRepo.save(post);
		Hostel h = hostelRepo.findById(hostel.getId()).get();
		PostResp postResp = new PostResp(post.getId(),
				new AccountRespone(post.getAccount().getUsername(), post.getAccount().getName(),
						post.getAccount().getAvatarUrl()),
				post.getTitle(), post.getContent(), post.getNumberOfFavourites(), post.getStatus(), post.getPostTime(),
				post.getHostel().getId());
		return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(), h.getRentPrice(),
				h.getDepositPrice(), h.getStatus(), h.getDescription(),
				new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()), h.getElectricPrice(),
				h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId()));
	}

	@Override
	public HostelResp updateHostel(HostelRequest hostel) {
		Optional<Hostel> optional = hostelRepo.findById(hostel.getId());
		if (optional.isPresent()) {
			Hostel newHostel = optional.get();
			newHostel.setAddress(hostel.getAddress());
			newHostel.setArea(hostel.getArea());
			newHostel.setCapacity(hostel.getCapacity());
			newHostel.setDepositPrice(hostel.getDepositPrice());
			newHostel.setDescription(hostel.getDescription());
			newHostel.setName(hostel.getName());
			newHostel.setRentPrice(hostel.getRentPrice());
			newHostel.setWaterPrice(hostel.getWaterPrice());
			newHostel.setRoomtype(roomTypeRepo.findById(hostel.getRoomTypeId()).get());

//			if (!hostel.getImages().isEmpty()) {
//				hostel.getImages().forEach(img -> {
//					img.setHostel(newHostel);
//					imageRepo.save(img);
//				});
//			}
			Hostel h = hostelRepo.save(newHostel);

			Post post = postRepo.findById(hostel.getId()).get();
			post.setContent(hostel.getPost().getContent());
			post.setTitle(hostel.getPost().getTitle());
			postRepo.save(post);
			PostResp postResp = new PostResp(post.getId(),
					new AccountRespone(post.getAccount().getUsername(), post.getAccount().getName(),
							post.getAccount().getAvatarUrl()),
					post.getTitle(), post.getContent(), post.getNumberOfFavourites(), post.getStatus(),
					post.getPostTime(), post.getHostel().getId());
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
					h.getElectricPrice(), h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId()));
		} else {
			throw new RuntimeException("Can't update Hostel, can't find hostel by id: " + hostel.getId());
		}
	}

	@Override
	public void deleteHostel(Long id) {
		imageRepo.findByHostelId(id).forEach(item -> imageRepo.deleteById(item.getId()));
		postRepo.deleteById(id);
		hostelRepo.deleteById(id);
	}

	@Override
	public PagedResponse<HostelResp> getAllHostel(int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);
		PageRequest pageable = PageRequest.of(page, size);
		Page<Hostel> hostelsPage = hostelRepo.findAll(pageable);

		List<HostelResp> hostelResponse = new ArrayList<>(hostelsPage.getContent().size());

		hostelResponse = hostelsPage.getContent().stream().map(h -> {
			PostResp postResp = new PostResp(h.getPost().getId(),
					new AccountRespone(h.getPost().getAccount().getUsername(), h.getPost().getAccount().getName(),
							h.getPost().getAccount().getAvatarUrl()),
					h.getPost().getTitle(), h.getPost().getContent(), h.getPost().getNumberOfFavourites(),
					h.getPost().getStatus(), h.getPost().getPostTime(), h.getPost().getHostel().getId());
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
					h.getElectricPrice(), h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId()));
		}).collect(Collectors.toList());
		return new PagedResponse<>(hostelResponse, hostelsPage.getNumber(), hostelsPage.getSize(),
				hostelsPage.getTotalElements(), hostelsPage.getTotalPages(), hostelsPage.isLast());
	}

	@Override
	public HostelResp getHostelRespById(Long id) {
		Optional<Hostel> hostel = hostelRepo.findById(id);
		if (hostel.isPresent()) {
			Hostel h = hostel.get();
			Post post = postRepo.findById(h.getId()).get();
			PostResp postResp = new PostResp(post.getId(),
					new AccountRespone(post.getAccount().getUsername(), post.getAccount().getName(),
							post.getAccount().getAvatarUrl()),
					post.getTitle(), post.getContent(), post.getNumberOfFavourites(), post.getStatus(),
					post.getPostTime(), post.getHostel().getId());
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
					h.getElectricPrice(), h.getWaterPrice(), postResp, h.getImages());
		} else
		throw new BadRequestException("Hostel id: " + id + " khong ton tai trong he thong");
	}

	@Override
	public HostelResp getHostelByPostId(long id) {
		Hostel h = hostelRepo.findByPostId(id)
				.orElseThrow(() -> new BadRequestException("Post id: " + id + " khong ton tai trong he thong"));
		Post post = postRepo.findById(h.getId()).get();
		PostResp postResp = new PostResp(post.getId(),
				new AccountRespone(post.getAccount().getUsername(), post.getAccount().getName(),
						post.getAccount().getAvatarUrl()),
				post.getTitle(), post.getContent(), post.getNumberOfFavourites(), post.getStatus(), post.getPostTime(),
				post.getHostel().getId());
		return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(), h.getRentPrice(),
				h.getDepositPrice(), h.getStatus(), h.getDescription(),
				new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()), h.getElectricPrice(),
				h.getWaterPrice(), postResp, h.getImages());

	}

	@Override
	public PagedResponse<HostelResp> findByManyOption(int page, int size, String address, double areaMin, double areMax,
			double minRent, double maxRent, int capacity, long idRoomType) {
		AppUtils.validatePageNumberAndSize(page, size);
		PageRequest pageable = PageRequest.of(page, size);
		Page<Hostel> hostelsPage;
		if(idRoomType == (long) 0)
			hostelsPage = hostelRepo.findByManyOption2(address, areaMin, areMax, minRent, maxRent, capacity, pageable);
		
		else {
			hostelsPage = hostelRepo.findByManyOptionWithRoomTypeId(address, areaMin, areMax, minRent, maxRent, capacity,
					idRoomType, pageable);
		}
		List<HostelResp> hostelResponse = new ArrayList<>(hostelsPage.getContent().size());

		hostelResponse = hostelsPage.getContent().stream().map(h -> {
			PostResp postResp = new PostResp(h.getPost().getId(),
					new AccountRespone(h.getPost().getAccount().getUsername(), h.getPost().getAccount().getName(),
							h.getPost().getAccount().getAvatarUrl()),
					h.getPost().getTitle(), h.getPost().getContent(), h.getPost().getNumberOfFavourites(),
					h.getPost().getStatus(), h.getPost().getPostTime(), h.getPost().getHostel().getId());
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
					h.getElectricPrice(), h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId()));
		}).collect(Collectors.toList());
		return new PagedResponse<HostelResp>(hostelResponse, hostelsPage.getNumber(), hostelsPage.getSize(),
				hostelsPage.getTotalElements(), hostelsPage.getTotalPages(), hostelsPage.isLast());
	}

	

	@Override
	public HostelResp updateStatusHostel(long id, int status) {
		Optional<Hostel> hostel = hostelRepo.findById(id);
		if (hostel.isPresent()) {
			Hostel h = hostel.get();
			h.setStatus(status);
			h = hostelRepo.save(h);
			Post post = postRepo.findById(h.getId()).get();
			PostResp postResp = new PostResp(post.getId(),
					new AccountRespone(post.getAccount().getUsername(), post.getAccount().getName(),
							post.getAccount().getAvatarUrl()),
					post.getTitle(), post.getContent(), post.getNumberOfFavourites(), post.getStatus(),
					post.getPostTime(), post.getHostel().getId());
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
					h.getElectricPrice(), h.getWaterPrice(), postResp, h.getImages());
		}
		throw new AppException("Không thể cập nhật, id không tồn tại");
	}

	@Override
	public PagedResponse<HostelResp> getHostelByStatus(int page, int size, int status) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<Hostel> hostelPage = hostelRepo.findByStatus(status, pageable);
		List<HostelResp> hostelResponse = new ArrayList<HostelResp>(hostelPage.getContent().size());
		hostelResponse = hostelPage.getContent().stream().map(h -> {
			PostResp postResp = new PostResp(h.getPost().getId(),
					new AccountRespone(h.getPost().getAccount().getUsername(), h.getPost().getAccount().getName(),
							h.getPost().getAccount().getAvatarUrl()),
					h.getPost().getTitle(), h.getPost().getContent(), h.getPost().getNumberOfFavourites(),
					h.getPost().getStatus(), h.getPost().getPostTime(), h.getPost().getHostel().getId());
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
					h.getElectricPrice(), h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId()));
		}).collect(Collectors.toList());
		return new PagedResponse<HostelResp>(hostelResponse, hostelPage.getNumber(), hostelPage.getSize(),
				hostelPage.getTotalElements(), hostelPage.getTotalPages(), hostelPage.isLast());
	}

	@Override
	public PagedResponse<HostelResp> getHostelByHostelStatusAndPostStatus(int page, int size, int status,
			int postStatus) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<Hostel> hostelPage = hostelRepo.findByStatusAndPostStatus(status, postStatus, pageable);
		List<HostelResp> hostelResponse = new ArrayList<HostelResp>(hostelPage.getContent().size());
		hostelResponse = hostelPage.getContent().stream().map(h -> {
			PostResp postResp = new PostResp(h.getPost().getId(),
					new AccountRespone(h.getPost().getAccount().getUsername(), h.getPost().getAccount().getName(),
							h.getPost().getAccount().getAvatarUrl()),
					h.getPost().getTitle(), h.getPost().getContent(), h.getPost().getNumberOfFavourites(),
					h.getPost().getStatus(), h.getPost().getPostTime(), h.getPost().getHostel().getId());
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
					h.getElectricPrice(), h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId()));
		}).collect(Collectors.toList());
		return new PagedResponse<HostelResp>(hostelResponse, hostelPage.getNumber(), hostelPage.getSize(),
				hostelPage.getTotalElements(), hostelPage.getTotalPages(), hostelPage.isLast());
	}

	@Override
	public PagedResponse<HostelResp> findByManyOptionAdmin(int page, int size, String address, double areaMin,
			double areMax, double minRent, double maxRent, int capacity, long idRoomType) {
		AppUtils.validatePageNumberAndSize(page, size);
		PageRequest pageable = PageRequest.of(page, size);
		Page<Hostel> hostelsPage;
		if(idRoomType == (long) 0)
			hostelsPage = hostelRepo.findByManyOptionAdmin2(address, areaMin, areMax, minRent, maxRent, capacity, pageable);
		
		else {
			hostelsPage = hostelRepo.findByManyOptionWithRoomTypeIdAdmin(address, areaMin, areMax, minRent, maxRent, capacity,
					idRoomType, pageable);
		}
		List<HostelResp> hostelResponse = new ArrayList<>(hostelsPage.getContent().size());

		hostelResponse = hostelsPage.getContent().stream().map(h -> {
			PostResp postResp = new PostResp(h.getPost().getId(),
					new AccountRespone(h.getPost().getAccount().getUsername(), h.getPost().getAccount().getName(),
							h.getPost().getAccount().getAvatarUrl()),
					h.getPost().getTitle(), h.getPost().getContent(), h.getPost().getNumberOfFavourites(),
					h.getPost().getStatus(), h.getPost().getPostTime(), h.getPost().getHostel().getId());
			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
					h.getElectricPrice(), h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId()));
		}).collect(Collectors.toList());
		return new PagedResponse<HostelResp>(hostelResponse, hostelsPage.getNumber(), hostelsPage.getSize(),
				hostelsPage.getTotalElements(), hostelsPage.getTotalPages(), hostelsPage.isLast());
	}

//	@Override
//	public List<HostelResp> getListHostelFavouriteByUsername(String username) {
//	
//		List<Hostel> hostels = hostelRepo.findHostelFavouriteByUseName(username);
//		List<HostelResp> hostelResponse = new ArrayList<HostelResp>(hostels.size());
//		hostelResponse = hostels.stream().map(h -> {
//			PostResp postResp = new PostResp(h.getPost().getId(),
//					new AccountRespone(h.getPost().getAccount().getUsername(), h.getPost().getAccount().getName(),
//							h.getPost().getAccount().getAvatarUrl()),
//					h.getPost().getTitle(), h.getPost().getContent(), h.getPost().getNumberOfFavourites(),
//					h.getPost().getStatus(), h.getPost().getPostTime(), h.getPost().getHostel().getId());
//			return new HostelResp(h.getId(), h.getName(), h.getCapacity(), h.getArea(), h.getAddress(),
//					h.getRentPrice(), h.getDepositPrice(), h.getStatus(), h.getDescription(),
//					new RoomtypeResponse(h.getRoomtype().getId(), h.getRoomtype().getRoomTypeName()),
//					h.getElectricPrice(), h.getWaterPrice(), postResp, imageRepo.findByHostelId(h.getId()));
//		}).collect(Collectors.toList());
//		return hostelResponse;
//	}
}
