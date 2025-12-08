package com.military.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.military.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	
	
	@Query("SELECT p FROM Purchase p WHERE p.base.id = :baseId")
	List<Purchase> findByBaseId(@Param("baseId") Integer baseId);
	
	
	@Query("SELECT p FROM Purchase p WHERE p.equipment.id = :equipmentId")
	List<Purchase> findByEquipmentId(@Param("equipmentId") Integer equipmentId);
	
	
	@Query("SELECT p FROM Purchase p WHERE p.base.id = :baseId AND p.equipment.id = :equipmentId")
	List<Purchase> findByBaseIdAndEquipmentId(@Param("baseId") Integer baseId, @Param("equipmentId") Integer equipmentId);
	
	
	@Query("SELECT p FROM Purchase p WHERE p.purchaseDate >= :startDate AND p.purchaseDate <= :endDate")
	List<Purchase> findByPurchaseDateBetween(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);
	
	
	@Query("SELECT p FROM Purchase p WHERE " +
	       "(:baseId IS NULL OR p.base.id = :baseId) AND " +
	       "(:equipmentId IS NULL OR p.equipment.id = :equipmentId) AND " +
	       "(:startDate IS NULL OR p.purchaseDate >= :startDate) AND " +
	       "(:endDate IS NULL OR p.purchaseDate <= :endDate)")
	List<Purchase> findWithFilters(@Param("baseId") Integer baseId, 
	                                @Param("equipmentId") Integer equipmentId,
	                                @Param("startDate") java.sql.Date startDate,
	                                @Param("endDate") java.sql.Date endDate);
}