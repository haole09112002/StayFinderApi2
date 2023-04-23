package com.finalproject.StayFinderApi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Hostel;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {

	Optional<Hostel> findByPostId(Long postId);
	
	Page<Hostel> findByStatus(int status, Pageable pageable);
	
	@Query("select h from Hostel h inner join   Post p ON h.id = p.id where p.status = 1 and h.status = 1 ORDER BY p.postTime DESC")
	Page<Hostel> findByStatusAndPostStatus(int status, int postStatus, Pageable pageable);
	
	@Query(value = "SELECT h.* FROM stayfinder.hostel AS h INNER JOIN   stayfinder.post AS p ON h.id = p.hostel_id "
			+ "WHERE p.status = 1 AND h.status = 1 AND LOWER(h.address) LIKE LOWER(CONCAT('%', ?1,'%'))"
			+ "	AND h.area > ?2 AND h.area < ?3 AND h.rent_price > ?4 AND h.rent_price < ?5"
			+ "	AND h.capacity < ?6 AND h.room_type_id = ?7"
			+ " ORDER BY p.post_time DESC", nativeQuery = true)
	Page<Hostel> findByManyOptionWithRoomTypeId(String address, double minArea, double maxArea, double minRent, double maxRent, int capacity, long roomTypeId, Pageable pageable);
	
	@Query("select h from Hostel h inner join   Post p ON h.id = p.id where p.status = 1 and h.status = 1"
			+ " and LOWER(h.address) LIKE LOWER(CONCAT('%', ?1,'%')) "
			+ " AND h.area > ?2 AND h.area < ?3 "
			+ " AND h.rentPrice > ?4 AND h.rentPrice < ?5"
			+ "		AND h.capacity < ?6 "
			+ "	 ORDER BY p.postTime DESC")
	Page<Hostel> findByManyOption2(String address, double minArea, double maxArea, double minRent, double maxRent, int capacity, Pageable pageable);
	
		@Query(value = "SELECT h.* FROM stayfinder.hostel AS h INNER JOIN   stayfinder.post AS p ON h.id = p.hostel_id "
+ "WHERE   LOWER(h.address) LIKE LOWER(CONCAT('%', ?1,'%'))"
+ "	AND h.area > ?2 AND h.area < ?3 AND h.rent_price > ?4 AND h.rent_price < ?5"
+ "	AND h.capacity < ?6 AND h.room_type_id = ?7"
+ " ORDER BY p.post_time DESC", nativeQuery = true)
Page<Hostel> findByManyOptionWithRoomTypeIdAdmin(String address, double minArea, double maxArea, double minRent, double maxRent, int capacity, long roomTypeId, Pageable pageable);

@Query("select h from Hostel h inner join   Post p ON h.id = p.id where "
+ "  LOWER(h.address) LIKE LOWER(CONCAT('%', ?1,'%')) "
+ " AND h.area > ?2 AND h.area < ?3 "
+ " AND h.rentPrice > ?4 AND h.rentPrice < ?5"
+ "		AND h.capacity < ?6 "
+ "	 ORDER BY p.postTime DESC")
Page<Hostel> findByManyOptionAdmin2(String address, double minArea, double maxArea, double minRent, double maxRent, int capacity, Pageable pageable);
}
