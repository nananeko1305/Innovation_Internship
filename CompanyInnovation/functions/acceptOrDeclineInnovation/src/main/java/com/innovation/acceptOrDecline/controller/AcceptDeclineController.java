package com.innovation.acceptOrDecline.controller;

import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Status;
import com.innovation.acceptOrDecline.services.InnovationService;
import com.innovation.acceptOrDecline.services.SubmitService;
import com.innovation.getInnovation.config.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("acceptDeclineInnovation")
public class AcceptDeclineController {

    @Autowired
    private  InnovationService innovationService;

    @Autowired
    private SubmitService submitService;

    @Autowired
    private TokenUtils tokenUtils;


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
