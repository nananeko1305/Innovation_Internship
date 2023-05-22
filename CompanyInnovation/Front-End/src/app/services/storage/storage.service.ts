import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import AWS from 'aws-sdk';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(private router: Router) { }

  storeTokenData(token: string): void {
    sessionStorage.setItem("jwt", token);
 //   sessionStorage.setItem("refreshToken", refreshToken);
  }

  clearToken() {
    window.sessionStorage.setItem('jwt', "")
    window.sessionStorage.setItem('accessKey', "")
    window.sessionStorage.setItem('secretKey', "")
    window.sessionStorage.setItem('sessionToken', "")
  }

  getRoleFromToken(): string {
    const jwtToken = window.sessionStorage.getItem('jwt')
    if (jwtToken) {
      const tokenSplit = jwtToken.split('.')
      const decoded = decodeURIComponent(encodeURIComponent(window.atob(tokenSplit[1])))
      const obj = JSON.parse(decoded)
      let list = obj["cognito:groups"]
      return list[0]
    }
    return ""
  }

  getUsernameFromToken(): string {
    const jwtToken = window.sessionStorage.getItem('jwt')
    if (jwtToken) {
      const tokenSplit = jwtToken.split('.')
      const decoded = decodeURIComponent(encodeURIComponent(window.atob(tokenSplit[1])))
      const obj = JSON.parse(decoded)
      return obj["cognito:username"]
    }
    return ""
  }

  getExpirationDateFromToken(): any {
    const jwtToken = window.sessionStorage.getItem('jwt')
    if (jwtToken) {
      const tokenSplit = jwtToken.split('.')
      const decoded = decodeURIComponent(encodeURIComponent(window.atob(tokenSplit[1])))
      const obj = JSON.parse(decoded)
      return obj.exp
    }
    return ""
  }

  // getSubjectFromToken(): string {
  //   const jwtToken = window.sessionStorage.getItem('jwt')
  //   if (jwtToken) {
  //     const tokenSplit = jwtToken.split('.')
  //     const decoded = decodeURIComponent(encodeURIComponent(window.atob(tokenSplit[1])))
  //     const obj = JSON.parse(decoded)
  //     return obj.sub
  //   }
  //   return ""
  // }

  getToken() {
    let dateFromJwt = this.getExpirationDateFromToken()
    if(!dateFromJwt) return ''
    const expDate = new Date(dateFromJwt * 1000)
    if (expDate < new Date()){
      this.clearToken()
      return ''
    }
    return sessionStorage.getItem("jwt")
  }

  storeTempCredentials(passedToken: string): void {
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
        sessionStorage.setItem("accessKey", AWS.config.credentials.accessKeyId);
        sessionStorage.setItem("secretKey", AWS.config.credentials.secretAccessKey);
        sessionStorage.setItem("sessionToken", AWS.config.credentials.sessionToken!);
        this.router.navigateByUrl("")
      }
})

  }

  getAccessKey(): string {
    const accessKey = window.sessionStorage.getItem('accessKey')
    if(accessKey) return accessKey
    return ""
  }

  getSecretKey(): string {
    const secretKey = window.sessionStorage.getItem('secretKey')
    if(secretKey) return secretKey
    return ""
  }

  getSessionToken(): string {
    const sessionToken = window.sessionStorage.getItem('sessionToken')
    if(sessionToken) return sessionToken
    return ""
  }
}
