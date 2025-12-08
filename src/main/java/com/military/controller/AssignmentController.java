package com.military.controller;

import org.springframework.web.bind.annotation.*;
import com.military.model.Assignment;
import com.military.service.AssignmentService;

@RestController
@RequestMapping("/assignment")
@CrossOrigin(origins = "http://localhost:5173")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    
    @PostMapping("/add")
    public Assignment addAssignment(@RequestBody Assignment assignment) {
        Integer baseId = assignment.getBase().getId();
        Integer equipmentId = assignment.getEquipment().getId();
        return assignmentService.saveAssignment(assignment, baseId, equipmentId);
    }
}
