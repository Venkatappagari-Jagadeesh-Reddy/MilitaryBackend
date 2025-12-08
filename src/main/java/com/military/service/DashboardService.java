package com.military.service;

import org.springframework.stereotype.Service;
import com.military.repository.AssetRepository;
import com.military.repository.PurchaseRepository;
import com.military.repository.TransferRepository;
import com.military.repository.AssignmentRepository;
import com.military.repository.ExpendRepository;
import com.military.dto.DashboardDto;

@Service
public class DashboardService {

    private final AssetRepository assetRepo;
    private final PurchaseRepository purchaseRepo;
    private final TransferRepository transferRepo;
    private final AssignmentRepository assignmentRepo;
    private final ExpendRepository expendRepo;

    public DashboardService(AssetRepository assetRepo,
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

    public DashboardDto getDashboard() {
        DashboardDto dto = new DashboardDto();

        dto.setOpeningBalance(assetRepo.findAll().stream().mapToInt(a -> a.getOpeningBalance()).sum());
        dto.setClosingBalance(assetRepo.findAll().stream().mapToInt(a -> a.getClosingBalance()).sum());
        dto.setTotalPurchases(purchaseRepo.findAll().stream().mapToInt(p -> p.getQuantity()).sum());
        dto.setTotalTransfersIn(transferRepo.findAll().size()); // can refine later
        dto.setTotalTransfersOut(transferRepo.findAll().size()); // can refine later
        dto.setTotalAssignments(assignmentRepo.findAll().size());
        dto.setTotalExpends(expendRepo.findAll().stream().mapToInt(e -> e.getQuantity()).sum());

        return dto;
    }
}
