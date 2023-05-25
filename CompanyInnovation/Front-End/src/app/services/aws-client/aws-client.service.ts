import { Injectable } from '@angular/core';
import { StorageService } from '../storage/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AwsClientService {

  constructor(private storageService:StorageService) { }

  sendRequest(receivedPath:string, receivedMethod:string, additionalParams:any, receivedBody:any = {}): any {
    let apigClientFactory = require('aws-api-gateway-client').default;

    let apigClient = apigClientFactory.newClient({
      invokeUrl:'https://7vf89k7mq0.execute-api.eu-north-1.amazonaws.com',
      accessKey: this.storageService.getAccessKey(), 
      secretKey: this.storageService.getSecretKey(), 
      sessionToken: this.storageService.getSessionToken(), 
      region: 'eu-north-1' 
    });

    additionalParams = {
      headers: {
        jwttoken : this.storageService.getToken()
      }

    }

    return apigClient.invokeApi({}, receivedPath, receivedMethod, additionalParams, receivedBody)
  }
}
