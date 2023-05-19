import { Injectable } from '@angular/core';
import { Amplify, Auth } from 'aws-amplify';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CognitoService {

  constructor() {
    Amplify.configure({
      Auth: environment.cognito
    })
   }


   public signUp(username: string, password: string, email: string, fullName: string): Promise<any>{
    return Auth.signUp({
        username: username,
        password: password,
        attributes: {
          email: email,
          fullName: fullName
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
}
