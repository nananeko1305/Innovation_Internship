package com.innovation.createInnovation.controller;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.innovation.createInnovation.DTO.InnovationDTO;
import com.innovation.createInnovation.services.SubmitService;
import com.innovation.getInnovation.config.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


    private TokenUtils tokenUtils;


    public SubmitController(SubmitService submitService , TokenUtils tokenUtils ) {
        this.submitService = submitService;
        this.tokenUtils = tokenUtils;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("")
    public ResponseEntity<?> submitInnovation (@RequestHeader("jwttoken") String bearerToken,@RequestBody @Valid InnovationDTO innovationModel, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<InnovationDTO>(submitService.submitInnovation(innovationModel, tokenUtils.getJWTClaimsSet(bearerToken.replace("Bearer ", ""))), HttpStatus.OK);
    }
}
