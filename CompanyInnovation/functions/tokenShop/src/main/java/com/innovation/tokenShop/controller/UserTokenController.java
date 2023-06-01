package com.innovation.tokenShop.controller;

import com.innovation.tokenShop.DTO.UserTokenDTO;
import com.innovation.tokenShop.config.TokenUtils;
import com.innovation.tokenShop.service.UserTokenService;
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
