package com.military.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.military.model.Asset;
import com.military.model.Purchase;
import com.military.model.Transfer;
import com.military.repository.AssetRepository;
import com.military.repository.PurchaseRepository;
import com.military.repository.TransferRepository;
import com.military.repository.AssignmentRepository;
import com.military.repository.ExpendRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final AssetRepository assetRepo;
    private final PurchaseRepository purchaseRepo;
    private final TransferRepository transferRepo;
    private final AssignmentRepository assignmentRepo;
    private final ExpendRepository expendRepo;

    public DashboardController(AssetRepository assetRepo,
                               PurchaseRepository purchaseRepo,
                               TransferRepository transferRepo,
                               AssignmentRepository assignmentRepo,
                               ExpendRepository expendRepo) {
        this.assetRepo = assetRepo;
        this.purchaseRepo = purchaseRepo;
        this.transferRepo = transferRepo;
        this.assignmentRepo = assignmentRepo;
        this.expendRepo = expendRepo;
    }

    
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard(
            @RequestParam(required = false) Integer baseId,
            @RequestParam(required = false) Integer equipmentId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        Map<String, Object> data = new HashMap<>();
        
        
        Date startDateObj = null;
        Date endDateObj = null;
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;
        
        if (startDate != null && !startDate.isEmpty()) {
            startDateObj = Date.valueOf(startDate);
            startTimestamp = new Timestamp(startDateObj.getTime());
        }
        if (endDate != null && !endDate.isEmpty()) {
            endDateObj = Date.valueOf(endDate);
            endTimestamp = new Timestamp(endDateObj.getTime());
        }
        
       
        List<Purchase> purchases;
        if (baseId != null || equipmentId != null || startDateObj != null || endDateObj != null) {
            purchases = purchaseRepo.findWithFilters(baseId, equipmentId, startDateObj, endDateObj);
        } else {
            purchases = purchaseRepo.findAll();
        }
        
       
        int totalPurchases = 0;
        for (var purchase : purchases) {
            totalPurchases += purchase.getQuantity();
        }
        data.put("totalPurchases", totalPurchases);
        
       
        List<Transfer> transfersIn;
        List<Transfer> transfersOut;
        if (baseId != null || equipmentId != null || startTimestamp != null || endTimestamp != null) {
            transfersIn = transferRepo.findTransfersInWithFilters(baseId, equipmentId, startTimestamp, endTimestamp);
            transfersOut = transferRepo.findTransfersOutWithFilters(baseId, equipmentId, startTimestamp, endTimestamp);
        } else {
            transfersIn = transferRepo.findAll();
            transfersOut = transferRepo.findAll();
        }
       
        int totalTransfersInQty = 0;
        for (var transfer : transfersIn) {
            totalTransfersInQty += transfer.getQuantity();
        }
        data.put("totalTransfersIn", totalTransfersInQty);
        data.put("totalTransfersInCount", transfersIn.size());
        
        
        int totalTransfersOutQty = 0;
        for (var transfer : transfersOut) {
            totalTransfersOutQty += transfer.getQuantity();
        }
        data.put("totalTransfersOut", totalTransfersOutQty);
        data.put("totalTransfersOutCount", transfersOut.size());
        
        
        int netMovement = totalPurchases + totalTransfersInQty - totalTransfersOutQty;
        data.put("netMovement", netMovement);
        
        
        int openingBalance = 0;
        int closingBalance = 0;
        List<Asset> assets = assetRepo.findAll();
        
        for (var asset : assets) {
           
            if (baseId != null && !asset.getBase().getId().equals(baseId)) {
                continue;
            }
          
            if (equipmentId != null && !asset.getEquipment().getId().equals(equipmentId)) {
                continue;
            }
            openingBalance += asset.getOpeningBalance();
            closingBalance += asset.getClosingBalance();
        }
        
        data.put("openingBalance", openingBalance);
        data.put("closingBalance", closingBalance);
        
        
        int totalAssignments = 0;
        if (baseId != null || equipmentId != null) {
            for (var assignment : assignmentRepo.findAll()) {
                if (baseId != null && !assignment.getBase().getId().equals(baseId)) {
                    continue;
                }
                if (equipmentId != null && !assignment.getEquipment().getId().equals(equipmentId)) {
                    continue;
                }
                totalAssignments++;
            }
        } else {
            totalAssignments = assignmentRepo.findAll().size();
        }
        data.put("totalAssignments", totalAssignments);
      
        int totalExpends = 0;
        if (baseId != null || equipmentId != null) {
            for (var expend : expendRepo.findAll()) {
                if (baseId != null && !expend.getBase().getId().equals(baseId)) {
                    continue;
                }
                if (equipmentId != null && !expend.getEquipment().getId().equals(equipmentId)) {
                    continue;
                }
                totalExpends += expend.getQuantity();
            }
        } else {
            for (var expend : expendRepo.findAll()) {
                totalExpends += expend.getQuantity();
            }
        }
        data.put("totalExpends", totalExpends);

        return data;
    }
}
