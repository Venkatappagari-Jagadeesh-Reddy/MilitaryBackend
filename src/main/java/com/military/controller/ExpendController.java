package com.military.controller;

import org.springframework.web.bind.annotation.*;
import com.military.model.Expend;
import com.military.service.ExpendService;

@RestController
@RequestMapping("/expend")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpendController {

    private final ExpendService expendService;

    public ExpendController(ExpendService expendService) {
        this.expendService = expendService;
    }

   
    @PostMapping("/add")
    public Expend addExpend(@RequestBody Expend expend) {
        return expendService.saveExpend(expend);
    }
}
