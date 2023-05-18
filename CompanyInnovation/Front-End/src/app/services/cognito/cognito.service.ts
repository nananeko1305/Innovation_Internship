import { Injectable } from '@angular/core';
import { Amplify, Auth } from 'aws-amplify';
import { environment } from 'src/environments/environment';
import AWS from 'aws-sdk';
import { AwsSignatureService } from '../aws-signature/aws-signature.service';
import { AwsSignatureInputData } from '../aws-signature/aws-signature-input.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import jsAwsSigV4 from 'js-aws-sigv4';
// import * as aws4 from "ngx-aws4";

@Injectable({
  providedIn: 'root'
})
export class CognitoService {

  constructor(private awsSignatureService:AwsSignatureService,private http: HttpClient) {
    Amplify.configure({
      Auth: environment.cognito
    })
   }


   public signUp(username: string, password: string, email: string): Promise<any>{
    return Auth.signUp({
        username: username,
        password: password,
        attributes: {
          email: email
        }
    })
   }

   public confirmSignUp(username: string, code: string) : Promise<any>{
    return Auth.confirmSignUp(username, code);
   }

   public signIn(username: string, password: string): Promise<any>{
    return Auth.signIn({
        username: username,
        password: password
    })
   }

   public getCredentials(passedToken: string) {

    if(AWS.config.credentials)
    (< AWS.CognitoIdentityCredentials > AWS.config.credentials).clearCachedId();
        AWS.config.region = 'eu-north-1';

AWS.config.credentials = new AWS.CognitoIdentityCredentials({
    IdentityPoolId: 'eu-north-1:62f51650-c68c-4636-aa3b-580b7c550f66',
    Logins: { 
        'cognito-idp.eu-north-1.amazonaws.com/eu-north-1_bQ6gcESHo': passedToken
    }
});


AWS.config.getCredentials( async (err) => {
  if (err) console.log(err.stack); // credentials not loaded
  else if (!AWS.config.credentials) return
  else {console.log("Access Key:", AWS.config.credentials.accessKeyId);
        console.log(AWS.config.credentials)
        var apigClientFactory = require('aws-api-gateway-client').default;

var apigClient = apigClientFactory.newClient({
  invokeUrl:'https://pik3lxnfrc.execute-api.eu-north-1.amazonaws.com',
  accessKey: AWS.config.credentials.accessKeyId, //'ACCESS_KEY',
  secretKey: AWS.config.credentials.secretAccessKey, //'SECRET_KEY',
  sessionToken: AWS.config.credentials.sessionToken, // 'SESSION_TOKEN', //OPTIONAL: If you are using temporary credentials you must include the session token
  region: 'eu-north-1' // OPTIONAL: The region where the API is deployed, by default this parameter is set to us-east-1
});

apigClient.invokeApi({}, '/prod/api', 'GET', {}, {})
    .then(function(result: any){
        //This is where you would put a success callback
        console.log(result)
    }).catch( function(result: any){
        //This is where you would put an error callback
        console.log(result)
    });

      }
})
   }
}
