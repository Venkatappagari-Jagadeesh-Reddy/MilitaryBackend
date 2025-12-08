package com.military.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.military.model.Purchase;
import com.military.repository.PurchaseRepository;
import com.military.service.PurchaseService;

@RestController
@RequestMapping("/purchase")
@CrossOrigin(origins = "http://localhost:5173")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final PurchaseRepository purchaseRepository;

    public PurchaseController(PurchaseService purchaseService, PurchaseRepository purchaseRepository) {
        this.purchaseService = purchaseService;
        this.purchaseRepository = purchaseRepository;
    }

    
    @PostMapping("/add")
    public String addPurchase(@RequestBody Purchase purchase) {
        purchaseService.savePurchase(purchase);
        return "Purchase Added Successfully!";
    }
    
  
    @GetMapping("/history")
    public List<Purchase> getPurchaseHistory(
            @RequestParam(required = false) Integer baseId,
            @RequestParam(required = false) Integer equipmentId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        Date startDateObj = null;
        Date endDateObj = null;
        
        if (startDate != null && !startDate.isEmpty()) {
            startDateObj = Date.valueOf(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            endDateObj = Date.valueOf(endDate);
        }
        
        if (baseId != null || equipmentId != null || startDateObj != null || endDateObj != null) {
            return purchaseRepository.findWithFilters(baseId, equipmentId, startDateObj, endDateObj);
        } else {
            return purchaseRepository.findAll();
        }
    }
}
