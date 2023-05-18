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
    public void addTokens(String userId) {

        UserToken user = userTokensRepository.findUser(userId);
        if (user == null) {

            userTokensRepository.saveUser(new UserToken(userId, 15));
        } else {

            user.setTokens(user.getTokens()+15);
        }
    }
}
