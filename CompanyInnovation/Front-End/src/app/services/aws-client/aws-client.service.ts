import { Injectable } from '@angular/core';
import { StorageService } from '../storage/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AwsClientService {

  constructor(private storageService:StorageService) { }

  sendRequest(receivedPath:string, receivedMethod:string, additionalParams:any = {}, receivedBody:any = {}): any {
    let apigClientFactory = require('aws-api-gateway-client').default;

    let apigClient = apigClientFactory.newClient({
      invokeUrl:'https://7vf89k7mq0.execute-api.eu-north-1.amazonaws.com',
      accessKey: this.storageService.getAccessKey(), //'ACCESS_KEY',
      secretKey: this.storageService.getSecretKey(), //'SECRET_KEY',
      sessionToken: this.storageService.getSessionToken(), // 'SESSION_TOKEN', //OPTIONAL: If you are using temporary credentials you must include the session token
      region: 'eu-north-1' // OPTIONAL: The region where the API is deployed, by default this parameter is set to us-east-1
    });


    additionalParams = {
      headers: {
        jwttoken : this.storageService.getToken()
      }
    }
    console.log(receivedBody)

    return apigClient.invokeApi({}, receivedPath, receivedMethod, additionalParams, receivedBody)
  }
}
