package com.innovation.acceptOrDecline.services;

import com.innovation.acceptOrDecline.entity.UserToken;
import com.innovation.acceptOrDecline.repository.UserTokensRepository;

public class UserTokenService {

    private final UserTokensRepository userTokensRepository;

    public UserTokenService(UserTokensRepository userTokensRepository) {
        this.userTokensRepository = userTokensRepository;
    }

    public void addTokens(UserToken userToken){
        userTokensRepository.save(userToken);
    }
}
