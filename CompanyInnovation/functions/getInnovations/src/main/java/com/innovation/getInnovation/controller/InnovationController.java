package com.innovation.getInnovation.controller;

import com.innovation.getInnovation.service.InnovationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("innovations")
public class InnovationController {

    @Autowired
    private InnovationService innovationService;

    @GetMapping()
    public String GetInnovation(){

        System.out.println(innovationService.GetAll());

        return "UserID: ";
    }

}
