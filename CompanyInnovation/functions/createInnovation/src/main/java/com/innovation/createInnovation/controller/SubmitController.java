package com.innovation.createInnovation.controller;

import com.innovation.createInnovation.DTO.InnovationDTO;
import com.innovation.createInnovation.services.SubmitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/submit")
public class SubmitController {

    private final SubmitService submitService;

    public SubmitController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @CrossOrigin("*")
    @PostMapping("")
    public ResponseEntity<?> submitInnovation (@RequestBody @Valid InnovationDTO innovationModel, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<InnovationDTO>(submitService.submitInnovation(innovationModel), HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("")
    public String hello() {
        return "Hello from submit innovation.";
    }
}
