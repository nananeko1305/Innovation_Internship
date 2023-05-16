package com.innovation.acceptOrDecline.services;

import com.amazonaws.services.kms.model.NotFoundException;
import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.InnovationEntity;
import com.innovation.acceptOrDecline.entity.Status;
import com.innovation.acceptOrDecline.repository.InnovationRepository;
import org.springframework.stereotype.Service;

@Service
public class InnovationService {
    private final InnovationRepository innovationRepository;


    public InnovationService(InnovationRepository innovationRepository) {
        this.innovationRepository = innovationRepository;
    }

    public InnovationEntity getById(Integer id) throws NotFoundException{
        return innovationRepository.findById(id);

    }

    public InnovationDTO updateStatus(InnovationDTO inovacija) {


        InnovationEntity entity = getById(inovacija.getId());
        entity.setStatus(entity.getStatus());
        innovationRepository.save(entity);
        return null;
    }
}