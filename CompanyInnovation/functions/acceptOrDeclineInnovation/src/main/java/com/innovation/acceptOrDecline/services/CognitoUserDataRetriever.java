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
    String accessKey="ASIA2ESAP36VAU4TZAGV";
    String secretKey="BhIqCZ+3p+UpNKWqayYJIDdQH7d8FwqGRkEbcA01";

    String sessionKey = "IQoJb3JpZ2luX2VjEMD//////////wEaCWV1LXdlc3QtMSJGMEQCIDzXX+hnkTquXw0xOKLDzpnHxb840unNeYRcmULufhjdAiBCVXy7FbK1IKrccuyb3DBW8lveHWwABdMvFePZTwpVhyqMAwjo//////////8BEAAaDDY5Njk5MzcwMTgwMiIM4iPIMuIYBRsWZB4xKuACzTeAPG1O4kaqeNT190fnboeJkB32xQ4ei+kuIninsEt/t8f6Tc1/liSZXLXYUw3jq8EVNMa6nUGvEhGAeT8riN75E9pxNQD8sIY5UY46BN3EIKmu6xYoYqF3jH+W7JPtfStUuOUSxlaB6L29r8GzE36QQcnR2lSrcMd/N+/S2YbjiSqlNId6NCM0CkV/mqqnfgBqvxe3z0cKnFqW8F215HGrn11pIMVQb7jBDpf6x49fApdzhVENUXFOH8/ORNpT2qFyvX4J4jTNuybvoertRT0+iwtD8HqS6K0mlXVk+rJvF5uIP8C3Gq7t4lUesF6+6/5YZSYQ0WaJm76rZlh8Mg/BZxAf47PIlbAEKXQxg6CO7nlN/e71d9eHBSGn5xZvLEQTHd95mq94ep99dAFGb1BzV8nZ8zk4KXEpt99g+goljqE3v/SpN59AGl8sfb59h8hAOkw36/PU3IavK176ETDy9LajBjqnAVoL1ZxlDPlyggP4JuDnxejNfqRbFlPvhOg1uvBWHgc6t/pbO1h9KAChqVgYE3feCHU1bIl8wlkpdM4rZhdwv8hCDdeFDlnWjKt2Qxy4SGtuUR2nXmN60wJA4Io3M4SqZP6Xvm9x8EFndvNxgAyzC2hvLgh9cydc1g+OS8nbKhHLbr5FwkthDtRWuI/C47lInPHDePKiq2DzhIGKlKQkApAFLkyidiJA";

    Regions region = Regions.EU_NORTH_1;

    String userPoolId = "eu-north-1_JNIspbxb3";

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



