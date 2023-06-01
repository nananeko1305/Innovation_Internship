package com.innovation.createInnovation.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

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
}
