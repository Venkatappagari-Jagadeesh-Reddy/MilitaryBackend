package com.military.service;

import org.springframework.stereotype.Service;

import com.military.model.Asset;
import com.military.model.AssetLog;
import com.military.model.Base;
import com.military.model.EquipmentType;
import com.military.model.Expend;
import com.military.repository.AssetLogRepository;
import com.military.repository.AssetRepository;
import com.military.repository.BaseRepository;
import com.military.repository.EquipmentTypeRepository;
import com.military.repository.ExpendRepository;

@Service
public class ExpendService {

    private final ExpendRepository expendRepository;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentRepository;
    private final AssetRepository assetRepository;
    private final AssetLogRepository assetLogRepository;

    public ExpendService(
            ExpendRepository expendRepository,
            BaseRepository baseRepository,
            EquipmentTypeRepository equipmentRepository,
            AssetRepository assetRepository,
            AssetLogRepository assetLogRepository) {

        this.expendRepository = expendRepository;
        this.baseRepository = baseRepository;
        this.equipmentRepository = equipmentRepository;
        this.assetRepository = assetRepository;
        this.assetLogRepository = assetLogRepository;
    }

    public Expend saveExpend(Expend expend) {
        // get base from database
        Base base = baseRepository.findById(expend.getBase().getId())
                .orElseThrow(() -> new RuntimeException("Base not found"));

        // get equipment from database
        EquipmentType equipment = equipmentRepository.findById(expend.getEquipment().getId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        // check if asset exists in base
        Asset asset = assetRepository
                .findByBaseIdAndEquipmentId(base.getId(), equipment.getId())
                .orElseThrow(() -> new RuntimeException("No stock available in base"));

        // check if enough stock available
        if (asset.getClosingBalance() < expend.getQuantity()) {
            throw new RuntimeException("Not enough stock to expend");
        }

        // update asset balance - reduce quantity
        int oldClosing = asset.getClosingBalance();
        asset.setOpeningBalance(oldClosing);
        int newClosing = oldClosing - expend.getQuantity();
        asset.setClosingBalance(newClosing);
        assetRepository.save(asset);

        // set dates
        expend.setExpendDate(new java.sql.Timestamp(System.currentTimeMillis()));
        expend.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
        
        // save expenditure
        Expend savedExpend = expendRepository.save(expend);

        // create log entry
        AssetLog log = new AssetLog();
        log.setType("EXPEND");
        log.setRefId(savedExpend.getId());
        log.setBaseId(base.getId());
        log.setEquipmentId(equipment.getId());
        log.setQuantity(expend.getQuantity());
        log.setCreatedBy(expend.getCreatedBy());
        assetLogRepository.save(log);

        return savedExpend;
    }
}
