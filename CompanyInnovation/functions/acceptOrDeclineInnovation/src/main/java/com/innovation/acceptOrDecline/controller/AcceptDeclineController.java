package com.innovation.acceptOrDecline.controller;

import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Status;
import com.innovation.acceptOrDecline.services.InnovationService;
import com.innovation.acceptOrDecline.services.SubmitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/status")
public class AcceptDeclineController {

    private final SubmitService submitService;
    private final InnovationService innovationService;
    public AcceptDeclineController(SubmitService submitService, InnovationService innovationService) {
        this.submitService = submitService;
        this.innovationService = innovationService;
    }



    @GetMapping("")
    public String test() {
        System.out.println("Console line function 1.");
        return "Hello from function1!";
    }

    //                                      Id radnika
    @CrossOrigin("*")                       // ^
    @PutMapping()    // /api/{id}/status?status=APPROVED ocekivani ishod?
    public ResponseEntity<?> updateStatus(@RequestBody @Valid InnovationDTO innovationModel) {


        //mail poslati

        //Azuriranje baze da se doda 15 poena korisniku

        return ResponseEntity.ok(innovationService.updateStatus(innovationModel));
    }
}




