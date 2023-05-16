package com.innovation.acceptOrDecline.services;

import com.amazonaws.services.kms.model.NotFoundException;
import com.innovation.acceptOrDecline.dto.InnovationDTO;
import com.innovation.acceptOrDecline.entity.InnovationEntity;
import com.innovation.acceptOrDecline.entity.Status;
import com.innovation.acceptOrDecline.repository.InnovationRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InnovationService {
    private final InnovationRepository innovationRepository;

    @Autowired
    private ModelMapper mapper;

    public InnovationService(InnovationRepository innovationRepository) {
        this.innovationRepository = innovationRepository;
    }

    public InnovationEntity getById(Integer id) throws NotFoundException{
        return innovationRepository.findById(id);

    }

    public InnovationDTO updateStatus(InnovationDTO inovacijaDTO) {

        InnovationEntity inovacija= new InnovationEntity();

        mapper.map(inovacijaDTO, inovacija);

        System.out.println(inovacija.toString());

       inovacija.setStatus(inovacija.getStatus());
        innovationRepository.save(inovacija);
        return null;
    }
}