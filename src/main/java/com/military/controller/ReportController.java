package com.military.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.model.Asset;
import com.military.model.Assignment;
import com.military.model.Purchase;
import com.military.model.Transfer;
import com.military.repository.AssetRepository;
import com.military.repository.AssignmentRepository;
import com.military.repository.PurchaseRepository;
import com.military.repository.TransferRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private final AssetRepository assetRepository;
	private final PurchaseRepository purchaseRepository;
	private final TransferRepository transferRepository;
	private final AssignmentRepository assignmentRepository;

	public ReportController(AssetRepository assetRepository,
	                        PurchaseRepository purchaseRepository,
	                        TransferRepository transferRepository,
	                        AssignmentRepository assignmentRepository) {
	    this.assetRepository = assetRepository;
	    this.purchaseRepository = purchaseRepository;
	    this.transferRepository = transferRepository;
	    this.assignmentRepository = assignmentRepository;
	}

    
    @GetMapping("/report/stock")
    public List<Asset> getStockReport() {
        return assetRepository.findAll();
    }
    
   
    @GetMapping("/report/purchases")
    public List<Purchase> getPurchaseHistory() {
        return purchaseRepository.findAll();
    }
    
    
    @GetMapping("/report/transfers")
    public List<Transfer> getTransferHistory() {
        return transferRepository.findAll();
    }
    
   
    @GetMapping("/report/assignments")
    public List<Assignment> getAssignmentHistory() {
        return assignmentRepository.findAll();
    }



}
