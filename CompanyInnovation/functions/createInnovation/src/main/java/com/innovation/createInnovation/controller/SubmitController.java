package com.innovation.createInnovation.controller;

import com.innovation.createInnovation.DTO.InnovationDTO;
import com.innovation.createInnovation.config.TokenUtils;
import com.innovation.createInnovation.services.SubmitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/submit")
public class SubmitController {

    public final TokenUtils tokenUtils;

    private final SubmitService submitService;

    public SubmitController(SubmitService submitService, TokenUtils tokenUtils) {
        this.submitService = submitService;
        this.tokenUtils = tokenUtils;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("")
    public ResponseEntity<?> submitInnovation (@RequestHeader("jwttoken") String jwtToken,@RequestBody @Valid InnovationDTO innovationModel, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<InnovationDTO>(submitService.submitInnovation(innovationModel, tokenUtils.getJWTClaimsSet(jwtToken)), HttpStatus.OK);
    }
}
