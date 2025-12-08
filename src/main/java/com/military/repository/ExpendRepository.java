package com.military.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.military.model.Expend;

public interface ExpendRepository extends JpaRepository<Expend, Integer> {
	
}