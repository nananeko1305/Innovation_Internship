package com.innovation.getInnovation.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.context.annotation.Configuration;
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

    public static String getRoleFromToken(JWTClaimsSet jwtClaimsSet){

        List<String> groups = null;
        try {
            groups = jwtClaimsSet.getStringListClaim("cognito:groups");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String employeeGroup = null;
        if (groups != null && !groups.isEmpty()) {
            employeeGroup = groups.get(0);
        }

        return employeeGroup;
    }

    public static String getEmailFromToken(JWTClaimsSet jwtClaimsSet){

        try {
            System.out.println(jwtClaimsSet.getStringClaim("email"));
            return jwtClaimsSet.getStringClaim("email");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getIdFromToken(JWTClaimsSet jwtClaimsSet){
        return jwtClaimsSet.getSubject();
    }

}
