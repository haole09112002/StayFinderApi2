package com.finalproject.StayFinderApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByHostelId(Long id);
}
