package com.innovation.acceptOrDecline.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CognitoUserDataRetriever {
    String accessKey="ASIA2ESAP36VGPEIPH2V";
    String secretKey="BrxMvWZ4/0VHdg2+6t6HnXOwvjcHkrynMrGDjHQg";

    String sessionKey = "IQoJb3JpZ2luX2VjEI///////////wEaCWV1LXdlc3QtMSJIMEYCIQDiEis8Ed4n8TIhSm4tR76Tliv72gZ/KWnhapIogofy/wIhAOggY+21b/K1wzJ7ac394JIBXSNxXZ3HfizteKssw7TfKpgDCLf//////////wEQABoMNjk2OTkzNzAxODAyIgyIDdTQsmlGEaPbUUUq7AKkyT9/UzXOSFhfr6FbvPjudRYsC/1id2vESl0ki5YFp3lnD5ezpDg/76SvQCXOBsgXaunRfGndpvXwA10XuTXbC+dTaa95lbuWct3xQ0RwgnszcfWb9dFCmpBEmmEZk46EeSlYJ91xe53oI7s4+l+ByMmwglMfNyPogVNhL+aCBqfrKWmfvpYtbZS6Nh1CitcNJkcFGsRktD7tQ71BYlhbC6q19UaNJBjONRsPwuadG8Vyq5Loj9esupmvR6UFe5fn2u7osur90lBRdUdOYV1JYt79Uw9IwiocqqBXCIQ5GKAUV9GFsbo0zgRxJl2dIUEfhbk17SR3616EPoDhebQHW+/TbGQxBWOPZA1iORvgnNKW4+BmMYxuAWpMAtsKcgDdyr+ATVt4+oMHoJtsktNaeHwUDybfIU5+ozs7oufP/mFftjvKVkHu7aNjT4Pagxr5ce/7ZIu84kCWcGIeA9mhYz/2H/65W6yEOvSUMKKRrKMGOqUBHAMRnwnMrDAByriCK0OGY0Y3al9CsZ0Jbk6RtauZs8pBcugwWBFCT1AAWafL4rtGWs/YUmQmRiHnGZ2Nzpa0NuoBjbVGyCvzM+0mdeGk+PQUr6YwYY0CbvybZ5yqe2EVSmYVpBR0+IcknAX+ouSuS6BAb/AwuOZsfi0Cxi4HAfsHLSIArXXwjrYIdz93QDHWsGFXTi0i6jBkZly1D/cROWuREAH6";

    Regions region = Regions.EU_NORTH_1;

    String userPoolId = "eu-north-1_bQ6gcESHo";

    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(accessKey, secretKey, sessionKey);


    AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
            .withRegion(region)
            .build();


    public String getUser (String username){
        AdminGetUserRequest request = new AdminGetUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username);

        AdminGetUserResult result = cognitoClient.adminGetUser(request);
        List<AttributeType> attributesList =  result.getUserAttributes();
        for (AttributeType a : attributesList){
            if (a.getName().equals("email")) {
                return a.getValue();
            }
        }

        return null;
        }


    }



