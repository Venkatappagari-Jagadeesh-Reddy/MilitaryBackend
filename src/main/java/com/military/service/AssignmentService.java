package com.military.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.military.model.Asset;
import com.military.model.AssetLog;
import com.military.model.Assignment;
import com.military.model.Base;
import com.military.model.EquipmentType;
import com.military.repository.AssetLogRepository;
import com.military.repository.AssetRepository;
import com.military.repository.AssignmentRepository;
import com.military.repository.BaseRepository;
import com.military.repository.EquipmentTypeRepository;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentRepository;
    private final AssetRepository assetRepository;
    private final AssetLogRepository assetLogRepository;

    public AssignmentService(
            AssignmentRepository assignmentRepository,
            BaseRepository baseRepository,
            EquipmentTypeRepository equipmentRepository,
            AssetRepository assetRepository,
            AssetLogRepository assetLogRepository) {

        this.assignmentRepository = assignmentRepository;
        this.baseRepository = baseRepository;
        this.equipmentRepository = equipmentRepository;
        this.assetRepository = assetRepository;
        this.assetLogRepository = assetLogRepository;
    }

    public Assignment saveAssignment(Assignment assignment, Integer baseId, Integer equipmentId) {
        // get base from database
        Base base = baseRepository.findById(baseId)
                .orElseThrow(() -> new RuntimeException("Base not found"));

        // get equipment from database
        EquipmentType equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        // set base and equipment to assignment
        assignment.setBase(base);
        assignment.setEquipment(equipment);
        assignment.setAssignDate(new Timestamp(System.currentTimeMillis()));
        assignment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        // check if asset exists in base
        Asset asset = assetRepository.findByBaseIdAndEquipmentId(baseId, equipmentId)
                .orElseThrow(() -> new RuntimeException("Asset not found in this base"));

        // check if enough stock available
        if (asset.getClosingBalance() < assignment.getQuantity()) {
            throw new RuntimeException("Not enough stock available");
        }

        // update asset balance - reduce quantity
        int oldClosing = asset.getClosingBalance();
        asset.setOpeningBalance(oldClosing);
        int newClosing = oldClosing - assignment.getQuantity();
        asset.setClosingBalance(newClosing);
        assetRepository.save(asset);

        // save assignment
        Assignment savedAssignment = assignmentRepository.save(assignment);

        // create log entry
        AssetLog log = new AssetLog();
        log.setType("ASSIGNMENT");
        log.setBaseId(baseId);
        log.setEquipmentId(equipmentId);
        log.setRefId(savedAssignment.getId());
        log.setQuantity(assignment.getQuantity());
        log.setCreatedBy(assignment.getCreatedBy());
        assetLogRepository.save(log);

        return savedAssignment;
    }
}
