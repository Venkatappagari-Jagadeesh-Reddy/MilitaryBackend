package com.military.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.military.model.Asset;


@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {

	Optional<Asset> findByBaseIdAndEquipmentId(Integer baseId, Integer equipmentId);
	
}
