package com.military.service;

import org.springframework.stereotype.Service;

import com.military.model.Asset;
import com.military.model.AssetLog;
import com.military.model.Base;
import com.military.model.EquipmentType;
import com.military.model.Purchase;
import com.military.repository.AssetLogRepository;
import com.military.repository.AssetRepository;
import com.military.repository.BaseRepository;
import com.military.repository.EquipmentTypeRepository;
import com.military.repository.PurchaseRepository;

@Service
public class PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final BaseRepository baseRepository;
	private final  EquipmentTypeRepository equipmentRepository;
	private final AssetRepository assetRepository;
	private final AssetLogRepository assetLogRepository;



	public PurchaseService(PurchaseRepository purchaseRepository,
            BaseRepository baseRepository,
            EquipmentTypeRepository equipmentRepository,
            AssetRepository assetRepository,
            AssetLogRepository assetLogRepository) {
this.purchaseRepository = purchaseRepository;
this.baseRepository = baseRepository;
this.equipmentRepository = equipmentRepository;
this.assetRepository = assetRepository;
this.assetLogRepository = assetLogRepository;
}

	public Purchase savePurchase(Purchase purchase) {
		// get base from database
		Base base = baseRepository.findById(purchase.getBaseId())
				.orElseThrow(() -> new RuntimeException("Base not found"));

		// get equipment from database
		EquipmentType equipment = equipmentRepository.findById(purchase.getEquipmentId())
				.orElseThrow(() -> new RuntimeException("Equipment not found"));

		// set base and equipment to purchase
		purchase.setBase(base);
		purchase.setEquipment(equipment);
		
		// set current date and time
		purchase.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
		if (purchase.getPurchaseDate() == null) {
		    purchase.setPurchaseDate(new java.sql.Date(System.currentTimeMillis()));
		}

		// save purchase to database
		Purchase savedPurchase = purchaseRepository.save(purchase);
		
		// find or create asset record
		Asset asset = assetRepository
	            .findByBaseIdAndEquipmentId(base.getId(), equipment.getId())
	            .orElse(null);

	    if (asset == null) {
	        // create new asset if not exists
	        asset = new Asset();
	        asset.setBase(base);
	        asset.setEquipment(equipment);
	        asset.setOpeningBalance(0);
	        asset.setClosingBalance(0);
	    }

	    // update asset balance
	    int oldClosing = asset.getClosingBalance();
	    asset.setOpeningBalance(oldClosing);
	    int newClosing = oldClosing + purchase.getQuantity();
	    asset.setClosingBalance(newClosing);
	    assetRepository.save(asset);

	    // create log entry for audit
	    AssetLog log = new AssetLog();
	    log.setType("PURCHASE");
	    log.setRefId(savedPurchase.getId());
	    log.setBaseId(base.getId());
	    log.setEquipmentId(equipment.getId());
	    log.setQuantity(purchase.getQuantity());
	    log.setCreatedBy(purchase.getCreatedBy());
	    assetLogRepository.save(log);

		return savedPurchase;
	}

}
