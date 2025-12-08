package com.military.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.military.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
	
	
}