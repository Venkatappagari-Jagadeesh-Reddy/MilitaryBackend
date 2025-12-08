package com.military.controller;

import org.springframework.web.bind.annotation.*;
import com.military.model.Transfer;
import com.military.service.TransferService;

@RestController
@RequestMapping("/transfer")
@CrossOrigin(origins = "http://localhost:5173")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/add")
    public Transfer addTransfer(@RequestBody Transfer transfer) {
        return transferService.saveTransfer(transfer);
    }
}
