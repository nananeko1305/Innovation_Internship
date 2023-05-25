package com.innovation.acceptOrDecline.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class TokenUtils {

    public JWTClaimsSet getJWTClaimsSet(String token) {
        try {
            JWT jwt = JWTParser.parse(token);
            return jwt.getJWTClaimsSet();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getEmailFromToken(JWTClaimsSet jwtClaimsSet){

        try {
            return jwtClaimsSet.getStringClaim("email");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

