package com.innovation.acceptOrDecline.services;


import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Innovation;
import com.innovation.acceptOrDecline.repository.InnovationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InnovationService {

    @Autowired
    private SubmitService submitService;

    private final InnovationRepository innovationRepository;

    public InnovationService(InnovationRepository innovationRepository) {
        this.innovationRepository = innovationRepository;
    }

    public InnovationDTO updateStatus(InnovationDTO innovationDTO) {
        innovationRepository.save(new Innovation(innovationDTO));
        submitService.submitComment(innovationDTO);
        if (innovationDTO.getStatus().toString().equals("APPROVED")){

        }
        return innovationDTO;
    }
}