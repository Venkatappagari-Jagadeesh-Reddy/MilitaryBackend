package com.military.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.military.model.EquipmentType;
import com.military.repository.EquipmentTypeRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class EquipmentController {

    private final EquipmentTypeRepository equipmentTypeRepository;

    public EquipmentController(EquipmentTypeRepository equipmentTypeRepository) {
        this.equipmentTypeRepository = equipmentTypeRepository;
    }

    
    @GetMapping("/equipment")
    public List<EquipmentType> getAllEquipment() {
        return equipmentTypeRepository.findAll();
    }
}

