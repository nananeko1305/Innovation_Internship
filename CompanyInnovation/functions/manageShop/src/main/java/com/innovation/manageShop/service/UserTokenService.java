package com.innovation.manageShop.service;

import com.innovation.manageShop.DTO.UserTokenDTO;
import com.innovation.manageShop.entity.UserTokenEntity;
import com.innovation.manageShop.repository.UserTokensRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {

    private final UserTokensRepository userTokensRepository;
    private final ModelMapper mapper;

    public UserTokenService(UserTokensRepository userTokensRepository, ModelMapper mapper) {
        this.userTokensRepository = userTokensRepository;
        this.mapper = mapper;
    }


    public UserTokenDTO getUserTokens(String userId) {
        return mapper.map(userTokensRepository.findUser(userId), UserTokenDTO.class);

    }

    public UserTokenDTO updateTokens(UserTokenDTO userTokenDTO, int tokensValue) {
        UserTokenEntity userTokenEntity = userTokensRepository.findUser(userTokenDTO.getUserId());
        userTokenEntity.setTokens(userTokenEntity.getTokens() - tokensValue);
        userTokensRepository.saveUser(userTokenEntity);
        return mapper.map(userTokenEntity, UserTokenDTO.class);
    }
}
