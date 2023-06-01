package org.innovation;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class AddEmployeeMembership implements RequestHandler<Map<String,Object>, Map<String,Object>>{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public Map<String,Object> handleRequest(Map<String,Object> event, Context context)
    {
        String lambdaInputJsonStr = gson.toJson(event);
        JsonObject jsonObject = JsonParser.parseString(lambdaInputJsonStr).getAsJsonObject();
        String userPoolId = String.valueOf(jsonObject.getAsJsonPrimitive("userPoolId"));
        String username = String.valueOf(jsonObject.getAsJsonPrimitive("userName"));
        String userPoolIdCorrected = userPoolId.substring(1, userPoolId.length() - 1);
        String usernameCorrected = username.substring(1, username.length() - 1);
        AWSCognitoIdentityProvider identityProviderClient = AWSCognitoIdentityProviderClientBuilder.defaultClient();
        AdminAddUserToGroupRequest adminAddUserToGroupRequest = new AdminAddUserToGroupRequest();
        adminAddUserToGroupRequest.setGroupName("Employee");
        adminAddUserToGroupRequest.setUserPoolId(userPoolIdCorrected);
        adminAddUserToGroupRequest.setUsername(usernameCorrected);
        identityProviderClient.adminAddUserToGroup(adminAddUserToGroupRequest);
        return event;
    }
}
