package com.innovation.acceptOrDecline.services;

import com.innovation.acceptOrDecline.entity.UserToken;
import com.innovation.acceptOrDecline.repository.UserTokensRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService implements IUserTokenService{

    private final UserTokensRepository userTokensRepository;

    public UserTokenService(UserTokensRepository userTokensRepository) {
        this.userTokensRepository = userTokensRepository;
    }


    @Override
    public void addTokens(UserToken userToken) {
            userTokensRepository.save(userToken);
    }
}
