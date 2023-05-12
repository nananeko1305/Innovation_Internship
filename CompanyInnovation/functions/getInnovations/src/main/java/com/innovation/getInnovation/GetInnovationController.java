package com.innovation.getInnovation;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("getInnovation")
public class GetInnovationController {

    @GetMapping()
    public String GetInnovation(){
        return "Hi";
    }

    @PostMapping()
    public String PostInnovation(@RequestBody String test){
        return "Hi " + test;
    }
}
