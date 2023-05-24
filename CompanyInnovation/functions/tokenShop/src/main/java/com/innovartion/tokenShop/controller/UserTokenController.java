package com.innovartion.tokenShop.controller;

import com.innovartion.tokenShop.DTO.UserTokenDTO;
import com.innovartion.tokenShop.config.TokenUtils;
import com.innovartion.tokenShop.service.UserTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tokens")
public class UserTokenController {

    private final UserTokenService userTokenService;

    private final TokenUtils tokenUtils;

    public UserTokenController(UserTokenService userTokenService, TokenUtils tokenUtils) {
        this.userTokenService = userTokenService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping()
    @CrossOrigin("*")
    public UserTokenDTO getUsersTokens(@RequestHeader("jwttoken") String token){
        System.out.println(tokenUtils.getJWTClaimsSet(token).getSubject());
        return  userTokenService.getUserTokens(tokenUtils.getJWTClaimsSet(token).getSubject());
    }

    @CrossOrigin("*")
    @PostMapping()
    public ResponseEntity<?> updateTokens(@RequestBody UserTokenDTO userTokenDTO, @RequestHeader("jwttoken") String token){
        int tokens = userTokenService.getUserTokens(tokenUtils.getJWTClaimsSet(token).getSubject()).getTokens();
        if (tokens <= 0 || tokens < userTokenDTO.getTokens()){
            return new ResponseEntity<>("Not enough tokens", HttpStatus.valueOf(201));
        }
        return new ResponseEntity<>(userTokenService.updateTokens(userTokenDTO),HttpStatus.OK);
    }
}
