package com.innovartion.tokenShop.service;

import com.innovartion.tokenShop.DTO.UserTokenDTO;
import com.innovartion.tokenShop.entity.UserTokenEntity;
import com.innovartion.tokenShop.repository.UserTokensRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {

    private final UserTokensRepository userTokensRepository;
    private  final ModelMapper mapper;

    public UserTokenService(UserTokensRepository userTokensRepository, ModelMapper mapper) {
        this.userTokensRepository = userTokensRepository;
        this.mapper = mapper;
    }


    public UserTokenDTO getUserTokens(String userId){
        return mapper.map(userTokensRepository.findUser(userId), UserTokenDTO.class);

    }

    public UserTokenDTO updateTokens (UserTokenDTO userTokenDTO){
        UserTokenEntity userTokenEntity= userTokensRepository.findUser(userTokenDTO.getUserId());
        userTokenEntity.setTokens(userTokenEntity.getTokens() - userTokenDTO.getTokens());
        userTokensRepository.saveUser(userTokenEntity);
        return mapper.map(userTokenEntity, UserTokenDTO.class);
    }
}
