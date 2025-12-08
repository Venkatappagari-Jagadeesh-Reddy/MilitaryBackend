package com.military.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.military.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Integer> {
	
	
	@Query("SELECT t FROM Transfer t WHERE t.fromBase.id = :baseId")
	List<Transfer> findByFromBaseId(@Param("baseId") Integer baseId);
	
	
	@Query("SELECT t FROM Transfer t WHERE t.toBase.id = :baseId")
	List<Transfer> findByToBaseId(@Param("baseId") Integer baseId);
	
	
	@Query("SELECT t FROM Transfer t WHERE t.equipment.id = :equipmentId")
	List<Transfer> findByEquipmentId(@Param("equipmentId") Integer equipmentId);
	
	
	@Query("SELECT t FROM Transfer t WHERE " +
	       "(:baseId IS NULL OR t.toBase.id = :baseId) AND " +
	       "(:equipmentId IS NULL OR t.equipment.id = :equipmentId) AND " +
	       "(:startDate IS NULL OR t.transferDate >= :startDate) AND " +
	       "(:endDate IS NULL OR t.transferDate <= :endDate)")
	List<Transfer> findTransfersInWithFilters(@Param("baseId") Integer baseId,
	                                            @Param("equipmentId") Integer equipmentId,
	                                            @Param("startDate") java.sql.Timestamp startDate,
	                                            @Param("endDate") java.sql.Timestamp endDate);
	
	
	@Query("SELECT t FROM Transfer t WHERE " +
	       "(:baseId IS NULL OR t.fromBase.id = :baseId) AND " +
	       "(:equipmentId IS NULL OR t.equipment.id = :equipmentId) AND " +
	       "(:startDate IS NULL OR t.transferDate >= :startDate) AND " +
	       "(:endDate IS NULL OR t.transferDate <= :endDate)")
	List<Transfer> findTransfersOutWithFilters(@Param("baseId") Integer baseId,
	                                            @Param("equipmentId") Integer equipmentId,
	                                            @Param("startDate") java.sql.Timestamp startDate,
	                                            @Param("endDate") java.sql.Timestamp endDate);
}