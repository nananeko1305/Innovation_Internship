package com.innovation.createInnovation.controller;

import com.innovation.createInnovation.DTO.InnovationDTO;
import com.innovation.createInnovation.services.SubmitService;
import org.springframework.http.HttpHeaders;
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

    @CrossOrigin(origins = "*")
    @PostMapping("")
    public ResponseEntity<?> submitInnovation (@RequestHeader HttpHeaders headers,@RequestBody @Valid InnovationDTO innovationModel, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.forEach((key, values) -> {
            System.out.println("Header Name: " + key);
            System.out.println("Header Values: " + values);
        });
        return new ResponseEntity<InnovationDTO>(submitService.submitInnovation(innovationModel), HttpStatus.OK);
    }
}
