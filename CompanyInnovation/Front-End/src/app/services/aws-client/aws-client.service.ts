import { Injectable } from '@angular/core';
import { StorageService } from '../storage/storage.service';
import { Innovation } from 'src/app/model/innovation';

@Injectable({
  providedIn: 'root'
})
export class AwsClientService {

  constructor(private storageService:StorageService) { }

  sendRequest(receivedPath:string, receivedMethod:string, receivedBody:any = {}): any {
    var apigClientFactory = require('aws-api-gateway-client').default;

    var apigClient = apigClientFactory.newClient({
      invokeUrl:'https://5j10nowhj2.execute-api.eu-north-1.amazonaws.com',
      accessKey: this.storageService.getAccessKey(), //'ACCESS_KEY',
      secretKey: this.storageService.getSecretKey(), //'SECRET_KEY',
      sessionToken: this.storageService.getSessionToken(), // 'SESSION_TOKEN', //OPTIONAL: If you are using temporary credentials you must include the session token
      region: 'eu-north-1' // OPTIONAL: The region where the API is deployed, by default this parameter is set to us-east-1
    });

    console.log(receivedBody)
    
    return apigClient.invokeApi({}, receivedPath, receivedMethod, {}, receivedBody)   
  }
}
