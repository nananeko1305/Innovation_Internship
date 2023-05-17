import { Injectable } from '@angular/core';
import { Amplify, Auth } from 'aws-amplify';
import { environment } from 'src/environments/environment';
import AWS from 'aws-sdk';
import { AwsSignatureService } from '../aws-signature/aws-signature.service';
import { AwsSignatureInputData } from '../aws-signature/aws-signature-input.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
   
    {
      ( < AWS.CognitoIdentityCredentials > AWS.config.credentials).clearCachedId();
        alert("Something")
        AWS.config.region = 'eu-north-1';

AWS.config.credentials = new AWS.CognitoIdentityCredentials({
    IdentityPoolId: 'eu-north-1:62f51650-c68c-4636-aa3b-580b7c550f66',
    Logins: { 
        'cognito-idp.eu-north-1.amazonaws.com/eu-north-1_bQ6gcESHo': passedToken
    }
});


AWS.config.getCredentials( (err) => {
  if (err) console.log(err.stack); // credentials not loaded
  else if (!AWS.config.credentials) return
  else {console.log("Access Key:", AWS.config.credentials.accessKeyId);
        console.log(AWS.config.credentials)
        let awsSignatureInputData = new AwsSignatureInputData();
        awsSignatureInputData.method = 'GET'
        awsSignatureInputData.canonicalUri = '/prod/api'
        awsSignatureInputData.host = 'pik3lxnfrc.execute-api.eu-north-1.amazonaws.com'
        awsSignatureInputData.region = 'eu-north-1'
        awsSignatureInputData.service = 'pik3lxnfrc.execute-api.eu-north-1.amazonaws.com'
        awsSignatureInputData.accessKey = AWS.config.credentials.accessKeyId
        awsSignatureInputData.secretKey = AWS.config.credentials.secretAccessKey
        const result1:any = this.awsSignatureService.generateSignature(awsSignatureInputData)
        const headerss = new HttpHeaders(result1)
        this.http.get<any>('https://pik3lxnfrc.execute-api.eu-north-1.amazonaws.com/prod/api', { headers : headerss }).subscribe(data => {
            console.log(data.total);
         })
      }
})
    }

    else{
      AWS.config.region = 'eu-north-1';

AWS.config.credentials = new AWS.CognitoIdentityCredentials({
    IdentityPoolId: 'eu-north-1:62f51650-c68c-4636-aa3b-580b7c550f66',
    Logins: { 
        'cognito-idp.eu-north-1.amazonaws.com/eu-north-1_bQ6gcESHo': passedToken
    }
});


AWS.config.getCredentials( (err) => {
  if (err) console.log(err.stack); // credentials not loaded
  else if (!AWS.config.credentials) return
  else {console.log("Access Key:", AWS.config.credentials.accessKeyId);
        console.log(AWS.config.credentials)
        let awsSignatureInputData = new AwsSignatureInputData();
        awsSignatureInputData.method = 'GET'
        awsSignatureInputData.canonicalUri = '/prod/api'
        awsSignatureInputData.host = 'pik3lxnfrc.execute-api.eu-north-1.amazonaws.com'
        awsSignatureInputData.region = 'eu-north-1'
        awsSignatureInputData.service = 'pik3lxnfrc.execute-api.eu-north-1.amazonaws.com'
        awsSignatureInputData.accessKey = AWS.config.credentials.accessKeyId
        awsSignatureInputData.secretKey = AWS.config.credentials.secretAccessKey
        const result1:any = this.awsSignatureService.generateSignature(awsSignatureInputData)
        const headerss = new HttpHeaders(result1)
        this.http.get<any>('https://pik3lxnfrc.execute-api.eu-north-1.amazonaws.com/prod/api', { headers : headerss }).subscribe(data => {
            console.log(data.total);
         })
      }
})
    }


   }
}
