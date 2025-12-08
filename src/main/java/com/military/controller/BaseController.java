package com.military.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.military.model.Base;
import com.military.repository.BaseRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class BaseController {

    private final BaseRepository baseRepository;

    public BaseController(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

   
    @GetMapping("/bases")
    public List<Base> getAllBases() {
        return baseRepository.findAll();
    }
}

