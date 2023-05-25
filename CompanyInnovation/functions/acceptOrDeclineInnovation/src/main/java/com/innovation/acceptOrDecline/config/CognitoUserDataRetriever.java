package com.innovation.acceptOrDecline.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CognitoUserDataRetriever {

    @Value("${amazon.aws.accesskey}")
    private String awsAccessKey = "ASIA2ESAP36VLSLVVPVQ";
    @Value("${amazon.aws.secretkey}")
    private String awsSecretKey = "SjrnzcyBymjOFe7SuatHUOiil6CJT9O5hRdW1sX0";
    @Value("${amazon.aws.sessiontoken}")
    private String awsSessionToken = "IQoJb3JpZ2luX2VjENz//////////wEaCWV1LXdlc3QtMSJIMEYCIQDCT0wGYOIjAKGmSCf/rFtR3dgSiTWFcAHcdb3cjNyyggIhANv35VJ4uVhDNhjqA4mT9X808CnjulU+GQH7murUyrP7KokDCBQQABoMNjk2OTkzNzAxODAyIgx+NC+U5HcvxX81+roq5gKTluh3g+6btUhqcKRNViOY6yeJGI5ciVkvuzEvzxAY0KNbKVNlecLMmtzJvHHjZgdciJkQC/hv8U40m+LA0N2O9u0ei+B9cudOAjinxPVMFcE7kL06C7WS+0zUvjpSyk6y2iQJsMAf0RkjSOQOVUgd31Y0usSekdHTOJ4xG67Gwrumco48j92V5rxrU4izSWjfrIGzgkXh2pTOEvb38FU4I39MtyOAff+ktf3Izp9KnZ8hh+IrulrH5y5HjDykkB00LjgMAD89ubFGGqOZOluanwfQsozyHNxkAf4XUaJPDVm95670l+aN2CVgY0xT0XtxJDZAjsxMtkTFMooKNZfo/IAiZn2duE3BsJwv8mYzttRXoRFaDB8ZCwE4giOK7bSZRFskSy7SG/Ux7dmSqUVsFYnyhgG4ngxSi7grEdAe/IU8ss1BRQ2IH0IZZFvl4gxgqtOE71GSgbvC5o+Cwrt6lx+GY0J+MJWHvaMGOqUBRSIBsUalodbeO4CwLpRLyMhDuflu+rqnOfaG9jKWblOa6XEwcc2XoRiCWrRKYp27C9qJAqgy+KrEM1blgaRQc6v+qBDly8u0wztw/kn7KlsmINFPvqkL081mdXxvlzU0evS/kDdeX+fn9mvcc30AwH15UdtG0L+Hs7vARcfcn1wWln+AnUQahR0BZokAuGqOIVKTy02fyZM0BjHbL3HxuRbahHSG\n";
    Regions region = Regions.EU_NORTH_1;
    @Value("${pool.id}")
    private String userPoolId = "eu-north-1_JNIspbxb3";

//    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);


    BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(awsAccessKey, awsSecretKey, awsSessionToken);

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



