package com.innovation.getInnovation.service;

import com.innovation.getInnovation.domain.model.Innovation;

import java.util.List;

public interface InnovationServiceInterface {


    List<Innovation> GetInnovations(String id, String getType);

}
