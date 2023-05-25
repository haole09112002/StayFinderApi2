package com.finalproject.StayFinderApi.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.StayFinderApi.entity.Account;
import com.finalproject.StayFinderApi.entity.AccountStatusEnum;
import com.finalproject.StayFinderApi.entity.Comment;
import com.finalproject.StayFinderApi.entity.Hostel;
import com.finalproject.StayFinderApi.entity.HostelStatusEnum;
import com.finalproject.StayFinderApi.entity.Image;
import com.finalproject.StayFinderApi.entity.Position;
import com.finalproject.StayFinderApi.entity.PositionNameEnum;
import com.finalproject.StayFinderApi.entity.Post;
import com.finalproject.StayFinderApi.entity.PostStatusEnum;
import com.finalproject.StayFinderApi.entity.RoomType;
import com.finalproject.StayFinderApi.entity.Schedule;
import com.finalproject.StayFinderApi.repository.AccountRepository;
import com.finalproject.StayFinderApi.repository.CommentRepository;
import com.finalproject.StayFinderApi.repository.HostelRepository;
import com.finalproject.StayFinderApi.repository.ImageRepository;
import com.finalproject.StayFinderApi.repository.PositionRepository;
import com.finalproject.StayFinderApi.repository.PostRepository;
import com.finalproject.StayFinderApi.repository.RoomTypeRepository;
import com.finalproject.StayFinderApi.repository.ScheduleRepository;


@RestController
@RequestMapping("/api")
public class TestMyAPI {

	@Autowired
	private AccountRepository accReq;
	@Autowired
	private RoomTypeRepository roomTypeRepository;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private HostelRepository hostelRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	
	
	@SuppressWarnings("deprecation")
	@GetMapping("test/create-sample-data")
	public boolean getAll(){
		List<RoomType> roomTypes = new ArrayList<RoomType>();
		roomTypes.add(new RoomType(1, "Chung cư", null));
		roomTypes.add(new RoomType(2, "Trọ", null));
		roomTypes.add(new RoomType(3, "KTX", null));
		roomTypeRepository.saveAll(roomTypes);
		
		
		List<Position> positions = new ArrayList<Position>();
		positions.add(new Position(1,PositionNameEnum.ROLE_USER,null));
		positions.add(new Position(2,PositionNameEnum.ROLE_ADMIN,null));
		
		
		
		positionRepository.saveAll(positions);
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account(0, "test1", "test1@", "123456", true, AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 1), null, null, null));
		accounts.add(new Account(0, "test2", "test2@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235242", positionRepository.getReferenceById((long) 1), null, null, null));
		accounts.add(new Account(0, "test3", "test3@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 1), null, null, null));
		accounts.add(new Account(0, "test4", "test4@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 1), null, null, null));
		accounts.add(new Account(0, "test5", "test5@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 2), null, null, null));
		accounts.add(new Account(0, "test6", "test6@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 1), null, null, null));
		accounts.add(new Account(0, "test7", "test7@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 1), null, null, null));
		accounts.add(new Account(0, "test8", "test8@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 1), null, null, null));
		accounts.add(new Account(0, "test9", "test9@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 2), null, null, null));
		accounts.add(new Account(0, "test10", "test10@", "123456", true,  AccountStatusEnum.ENABLE.getValue(), 
				null, "0359235245", positionRepository.getReferenceById((long) 2), null, null, null));
		accReq.saveAll(accounts);
				
		Account account1 = accReq.findById((long)1).get();
		Account account3 = accReq.findById((long)3).get();
		List<Account> likeAccounts = new ArrayList<Account>();
		likeAccounts.add(account3);
		likeAccounts.add(account1);
		
		
		List<Hostel> hostels = new ArrayList<Hostel>();
		hostels.add(new Hostel(1, "Phong1", 3, 24.0, "12 Nguyen Luong Bang", 1800000.0, 500000.0, HostelStatusEnum.NO.getValue(), "Phong dep1",roomTypes.get(0), 3500, 8000, null, null));
		hostels.add(new Hostel(2, "Phong2", 1, 12.0, "97 Nguyen Luong Bang", 1000000.0, 200000.0,HostelStatusEnum.NO.getValue(), "Phong dep2",roomTypes.get(1), 3500, 8000, null, null));
		hostels.add(new Hostel(3, "Phong3", 3, 15.0, "12 Au Co", 1200000.0, 500000.0, HostelStatusEnum.NO.getValue(), "Phong dep3",roomTypes.get(1), 3500, 8000, null, null));
		hostels.add(new Hostel(4, "Phong4", 2, 24.0, "12 Nguyen Van Linh", 1700000.0, 250000.0, HostelStatusEnum.NO.getValue(), "Phong dep3",roomTypes.get(0), 3800, 8000, null, null));
		hostels.add(new Hostel(5, "Phong5", 1, 15.0, "K12 Nguyen Chanh", 1200000.0, 200000.0, HostelStatusEnum.NO.getValue(), "Phong dep2",roomTypes.get(0), 4000, 9000, null, null));
		hostels.add(new Hostel(6, "Phong6", 2, 30.0, "12 Nguyen Luong Bang", 2000000.0, 500000.0, HostelStatusEnum.NO.getValue(), "Phong dep5",roomTypes.get(2), 3500, 8000, null, null));
		hostels.add(new Hostel(7, "Phong7", 3, 35.0, "12 Nguyen Luong Bang", 1800000.0, 500000.0, HostelStatusEnum.NO.getValue(), "Phong dep2",roomTypes.get(0), 3500, 8000, null, null));
		hostels.add(new Hostel(8, "Phong8", 5, 45.0, "12 Nguyen Luong Bang", 3000000.0, 1000000.0, HostelStatusEnum.YES.getValue(), "Phong dep",roomTypes.get(2), 3200, 11000, null, null));
		hostels.add(new Hostel(9, "Phong9", 1, 12.0, "K82/103 Nguyen Luong Bang", 1800000.0, 600000.0, HostelStatusEnum.YES.getValue(), "Phong dep",roomTypes.get(0), 3500, 8000, null, null));
		hostels.add(new Hostel(10, "Phong10", 2, 20.0, "12 Ton Duc Thang", 1400000.0, 500000.0, HostelStatusEnum.YES.getValue(), "Phong dep",roomTypes.get(2), 3000, 8000, null, null));
		
		hostelRepository.saveAll(hostels);
		
		List<Image> images = new ArrayList<Image>();
		images.add(new Image(0, null, "anh1", hostelRepository.getById((long) 1)));
		images.add(new Image(0, null, "anh2", hostelRepository.getById((long) 1)));
		images.add(new Image(0, null, "anh3", hostelRepository.getById((long) 1)));
		images.add(new Image(0, null, "anh1", hostelRepository.getById((long) 2)));
		images.add(new Image(0, null, "anh2", hostelRepository.getById((long) 2)));
		images.add(new Image(0, null, "anh1", hostelRepository.getById((long) 6)));
		images.add(new Image(0, null, "anh2", hostelRepository.getById((long) 2)));
		
		images.add(new Image(0, null, "anh1", hostelRepository.getById((long) 3)));
		images.add(new Image(0, null, "anh2", hostelRepository.getById((long) 5)));	
		images.add(new Image(0, null, "anh1", hostelRepository.getById((long) 5)));
		images.add(new Image(0, null, "anh2", hostelRepository.getById((long) 7)));
		
		imageRepository.saveAll(images);
		
		List<Post> posts = new ArrayList<Post>();
		posts.add(new Post(1, accReq.getReferenceById((long) 1), "Phong Tro tai Au co", "Dep", likeAccounts.size(), PostStatusEnum.APPROVED.getValue(), new Date(), hostelRepository.getReferenceById((long)1), null, null, likeAccounts));
		posts.add(new Post(2, accReq.getReferenceById((long) 2), "Phong Tro tai Nguyen Luong Bang1", "Dep1", likeAccounts.size(), PostStatusEnum.APPROVED.getValue(), new Date(), hostelRepository.getReferenceById((long)2), null, null, likeAccounts));
		posts.add(new Post(3, accReq.getReferenceById((long) 1), "Phong Tro tai Nguyen Luong Bang2", "Dep2", 0, PostStatusEnum.APPROVED.getValue(), new Date(), hostelRepository.getReferenceById((long)3), null, null, null));
		posts.add(new Post(4, accReq.getReferenceById((long) 1), "Phong Tro tai Nguyen Luong Bang", "Dep3", 0, PostStatusEnum.APPROVED.getValue(), new Date(),hostelRepository.getReferenceById((long)4), null, null, null));
		posts.add(new Post(5, accReq.getReferenceById((long) 3), "Phong Tro tai Nguyen Luong Bang3", "De4p", 0, PostStatusEnum.APPROVED.getValue(), new Date(), hostelRepository.getReferenceById((long)5), null, null, null));
		posts.add(new Post(6, accReq.getReferenceById((long) 5), "Phong Tro tai Nguyen Luong Bang", "Dep", 0, PostStatusEnum.NOT_YET_APPROVED.getValue(), new Date(), hostelRepository.getReferenceById((long)6), null, null, null));
		posts.add(new Post(7, accReq.getReferenceById((long) 1), "Phong Tro tai Nguyen Luong Bang", "De4p", 0, PostStatusEnum.NOT_YET_APPROVED.getValue(), new Date(), hostelRepository.getReferenceById((long)7), null, null, null));
		posts.add(new Post(8, accReq.getReferenceById((long) 4), "Phong Tro tai Nguyen Luong Bang4", "De5p", 0, PostStatusEnum.NOT_YET_APPROVED.getValue(), new Date(),hostelRepository.getReferenceById((long)8), null, null, null));
		posts.add(new Post(9, accReq.getReferenceById((long) 4), "Phong Tro tai Nguyen Luong Bang5", "Dep6", 0, PostStatusEnum.NOT_APPROVED.getValue(), new Date(), hostelRepository.getReferenceById((long)9), null, null, null));
		posts.add(new Post(10, accReq.getReferenceById((long) 1), "Phong Tro tai Nguyen Luong Bang6", "Dep6", 0, PostStatusEnum.NOT_APPROVED.getValue(), new Date(), hostelRepository.getReferenceById((long)10), null, null, null));
		
		postRepository.saveAll(posts);
		
		List<Comment> comments = new ArrayList<Comment>();
		
		comments.add(new Comment(0, postRepository.getReferenceById((long)1), accReq.getReferenceById((long)1), "Phong dep1", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)1), accReq.getReferenceById((long)1), "Phong dep2", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)1), accReq.getReferenceById((long)2), "Phong dep2", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)2), accReq.getReferenceById((long)3), "Phong dep2", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)3), accReq.getReferenceById((long)1), "Phong dep2", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)4), accReq.getReferenceById((long)4), "Phong dep5", new Date(),null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)4), accReq.getReferenceById((long)5), "Phong dep5", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)5), accReq.getReferenceById((long)8), "Phong dep8", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)6), accReq.getReferenceById((long)7), "Phong dep2", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)7), accReq.getReferenceById((long)9), "Phong dep4", new Date(), null));
		comments.add(new Comment(0, postRepository.getReferenceById((long)8), accReq.getReferenceById((long)2), "Phong dep7", new Date(), null));
		
		commentRepository.saveAll(comments);
		
		List<Schedule> schedules = new ArrayList<Schedule>();
		schedules.add(new Schedule(0, accounts.get(0).getUsername(), "hao1", "0256554654", "Hen gap1", new Date(), postRepository.getReferenceById((long)1)));
		schedules.add(new Schedule(0,  accounts.get(1).getUsername(), "hao1", "0256554654", "Hen gap1", new Date(), postRepository.getReferenceById((long)1)));
		schedules.add(new Schedule(0,  accounts.get(1).getUsername(), "hao1", "0256554654", "Hen gap1", new Date(), postRepository.getReferenceById((long)2)));
		schedules.add(new Schedule(0,  accounts.get(1).getUsername(), "hao4", "0256554654", "Hen gap1", new Date(), postRepository.getReferenceById((long)4)));
		schedules.add(new Schedule(0,   accounts.get(0).getUsername(), "hao3", "0256554654", "Hen gap3", new Date(), postRepository.getReferenceById((long)3)));
		
		scheduleRepository.saveAll(schedules);
		return true;
	}
}
