package com.innovation.getInnovation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("getInnovation")
public class GetInnovationController {

    @GetMapping()
    public String GetInnovation(){
        return "Hi";
    }
}
