package com.innovation.getInnovation.controller;

import com.innovation.getInnovation.config.TokenUtils;
import com.innovation.getInnovation.domain.dto.InnovationDTO;
import com.innovation.getInnovation.service.InnovationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("innovations")
public class InnovationController {

    private final InnovationService innovationService;

    private final TokenUtils tokenUtils;

    public InnovationController(InnovationService innovationService, TokenUtils tokenUtils) {
        this.innovationService = innovationService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping()
    @CrossOrigin("*")
    public ResponseEntity<List<InnovationDTO>> GetInnovation(@RequestHeader("Authorization") String bearerToken){
        return new ResponseEntity<>(innovationService.convertToDtoList(innovationService.GetAll(tokenUtils.getJWTClaimsSet(bearerToken.replace("Bearer ", "")))), HttpStatus.OK);
    }

}
