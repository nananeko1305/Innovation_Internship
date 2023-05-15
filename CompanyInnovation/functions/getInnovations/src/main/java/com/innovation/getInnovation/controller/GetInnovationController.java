package com.innovation.getInnovation.controller;

import com.innovation.getInnovation.domain.model.Innovation;
import com.innovation.getInnovation.service.InnovationServiceInterface;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("innovations")
public class GetInnovationController {

    @Autowired
    private InnovationServiceInterface innovationServiceInterface;
    @GetMapping()
    public String GetInnovation(@RequestParam String userId){

        innovationServiceInterface.GetInnovations(userId, "");

        return "UserID: " + userId;
    }

}
