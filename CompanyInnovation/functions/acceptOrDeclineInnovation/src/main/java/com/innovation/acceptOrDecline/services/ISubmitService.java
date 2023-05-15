package com.innovation.acceptOrDecline.services;


import com.innovation.acceptOrDecline.dto.InnovationDTO;

public interface ISubmitService {
    InnovationDTO submitComment (InnovationDTO innovationDTO);
}
