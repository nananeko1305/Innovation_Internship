package com.innovation.manageShop.controller;

import com.innovation.manageShop.DTO.UserTokenDTO;
import com.innovation.manageShop.service.UserTokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tokens")
public class UserTokenController {

    private final UserTokenService userTokenService;

    public UserTokenController(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @GetMapping("{id}")
    @CrossOrigin("*")
    public UserTokenDTO getUsersTokens(@PathVariable("id") String userId) {
        return userTokenService.getUserTokens(userId);
    }

    @CrossOrigin("*")
    @PostMapping()
    public UserTokenDTO updateTokens(@RequestBody UserTokenDTO userTokenDTO, int tokensValue) {
        return userTokenService.updateTokens(userTokenDTO, tokensValue);
    }
}
