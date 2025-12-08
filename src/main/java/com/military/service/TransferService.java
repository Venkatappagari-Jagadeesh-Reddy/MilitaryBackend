package com.military.service;

import org.springframework.stereotype.Service;

import com.military.model.Asset;
import com.military.model.AssetLog;
import com.military.model.Base;
import com.military.model.EquipmentType;
import com.military.model.Transfer;
import com.military.repository.AssetLogRepository;
import com.military.repository.AssetRepository;
import com.military.repository.BaseRepository;
import com.military.repository.EquipmentTypeRepository;
import com.military.repository.TransferRepository;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentRepository;
    private final AssetRepository assetRepository;
    private final AssetLogRepository assetLogRepository;

    public TransferService(TransferRepository transferRepository,
                           BaseRepository baseRepository,
                           EquipmentTypeRepository equipmentRepository,
                           AssetRepository assetRepository,
                           AssetLogRepository assetLogRepository) {

        this.transferRepository = transferRepository;
        this.baseRepository = baseRepository;
        this.equipmentRepository = equipmentRepository;
        this.assetRepository = assetRepository;
        this.assetLogRepository = assetLogRepository;
    }

    public Transfer saveTransfer(Transfer transfer) {
        // get from base
        Base fromBase = baseRepository.findById(transfer.getFromBaseId())
                .orElseThrow(() -> new RuntimeException("From Base not found"));

        // get to base
        Base toBase = baseRepository.findById(transfer.getToBaseId())
                .orElseThrow(() -> new RuntimeException("To Base not found"));

        // get equipment
        EquipmentType equipment = equipmentRepository.findById(transfer.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        // check if asset exists in from base
        Asset fromAsset = assetRepository
                .findByBaseIdAndEquipmentId(fromBase.getId(), equipment.getId())
                .orElseThrow(() -> new RuntimeException("Asset not available in source base"));

        // check if enough stock available
        if (fromAsset.getClosingBalance() < transfer.getQuantity()) {
            throw new RuntimeException("Not enough stock in source base");
        }

        // update from base asset - reduce quantity
        int oldClosing = fromAsset.getClosingBalance();
        fromAsset.setOpeningBalance(oldClosing);
        fromAsset.setClosingBalance(oldClosing - transfer.getQuantity());
        assetRepository.save(fromAsset);

        // get or create asset in to base
        Asset toAsset = assetRepository
                .findByBaseIdAndEquipmentId(toBase.getId(), equipment.getId())
                .orElse(null);

        if (toAsset == null) {
            // create new asset if not exists
            toAsset = new Asset();
            toAsset.setBase(toBase);
            toAsset.setEquipment(equipment);
            toAsset.setOpeningBalance(0);
            toAsset.setClosingBalance(0);
        }

        // update to base asset - add quantity
        int toOldClosing = toAsset.getClosingBalance();
        toAsset.setOpeningBalance(toOldClosing);
        toAsset.setClosingBalance(toOldClosing + transfer.getQuantity());
        assetRepository.save(toAsset);

        // set transfer details
        transfer.setFromBase(fromBase);
        transfer.setToBase(toBase);
        transfer.setEquipment(equipment);
        transfer.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
        if (transfer.getTransferDate() == null) {
            transfer.setTransferDate(new java.sql.Timestamp(System.currentTimeMillis()));
        }

        // save transfer
        Transfer saved = transferRepository.save(transfer);

        // create log for transfer out
        AssetLog outLog = new AssetLog();
        outLog.setType("TRANSFER_OUT");
        outLog.setRefId(saved.getId());
        outLog.setBaseId(fromBase.getId());
        outLog.setEquipmentId(equipment.getId());
        outLog.setQuantity(transfer.getQuantity());
        outLog.setCreatedBy(transfer.getCreatedBy());
        assetLogRepository.save(outLog);
        
        // create log for transfer in
        AssetLog inLog = new AssetLog();
        inLog.setType("TRANSFER_IN");
        inLog.setRefId(saved.getId());
        inLog.setBaseId(toBase.getId());
        inLog.setEquipmentId(equipment.getId());
        inLog.setQuantity(transfer.getQuantity());
        inLog.setCreatedBy(transfer.getCreatedBy());
        assetLogRepository.save(inLog);

        return saved;
    }
}
