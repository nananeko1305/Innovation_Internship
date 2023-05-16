package com.innovation.acceptOrDecline.services;

import com.amazonaws.services.kms.model.NotFoundException;
import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.Innovation;
import com.innovation.acceptOrDecline.repository.InnovationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InnovationService {
    private final InnovationRepository innovationRepository;

    public InnovationService(InnovationRepository innovationRepository) {
        this.innovationRepository = innovationRepository;
    }

    public InnovationDTO updateStatus(InnovationDTO innovationDTO) {
        innovationRepository.save(new Innovation(innovationDTO));
        return innovationDTO;
    }
}