package com.finalproject.StayFinderApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.StayFinderApi.entity.Position;
import com.finalproject.StayFinderApi.entity.PositionNameEnum;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

	Optional<Position> findByPositionName(PositionNameEnum name);
}
