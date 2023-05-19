package com.innovation.acceptOrDecline.controller;

import com.innovation.acceptOrDecline.config.TokenUtils;
import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Status;
import com.innovation.acceptOrDecline.services.InnovationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("acceptDeclineInnovation")
public class AcceptDeclineController {

    private final InnovationService innovationService;
    private final TokenUtils tokenUtils;

    public AcceptDeclineController(InnovationService innovationService, TokenUtils tokenUtils) {
        this.innovationService = innovationService;
        this.tokenUtils = tokenUtils;
    }


    @CrossOrigin("*")
    @PutMapping()
    public ResponseEntity<InnovationDTO> updateStatus(@RequestHeader("jwttoken") String bearerToken, @RequestBody InnovationDTO innovationDTO) {
        if (innovationDTO.getStatus().equals(Status.DECLINED) || innovationDTO.getStatus().equals(Status.APPROVED)){
            return new ResponseEntity<>(innovationService.updateStatus(innovationDTO, tokenUtils.getJWTClaimsSet(bearerToken.replace("Bearer ", ""))), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
