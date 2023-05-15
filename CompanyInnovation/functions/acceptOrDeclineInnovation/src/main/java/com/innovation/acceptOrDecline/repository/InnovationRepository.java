package com.innovation.acceptOrDecline.repository;

import com.innovation.acceptOrDecline.entity.InnovationEntity;

public interface InnovationRepository {
    InnovationEntity findById(Integer id);
    void save(InnovationEntity entity);
    // Other methods for CRUD operations
}

