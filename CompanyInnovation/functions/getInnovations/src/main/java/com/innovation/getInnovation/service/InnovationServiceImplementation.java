package com.innovation.getInnovation.service;

import com.innovation.getInnovation.domain.model.Innovation;
import com.innovation.getInnovation.repository.InnovationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InnovationServiceImplementation implements InnovationService {

    @Autowired
    private InnovationRepository innovationRepository;

    @Override
    public List<Innovation> GetAll() {
        return innovationRepository.GetAll();

    }
}
