package com.innovation.acceptOrDecline.controller;

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

//    private final SubmitService submitService;

    public AcceptDeclineController(InnovationService innovationService/*, SubmitService submitService*/) {
        this.innovationService = innovationService;
//        this.submitService = submitService;
    }

    @CrossOrigin("*")
    @PutMapping()
    public ResponseEntity<InnovationDTO> updateStatus(@RequestBody InnovationDTO innovationDTO) {
        if (innovationDTO.getStatus().equals(Status.DECLINED) || innovationDTO.getStatus().equals(Status.APPROVED)){
            return new ResponseEntity<>(innovationService.updateStatus(innovationDTO), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
