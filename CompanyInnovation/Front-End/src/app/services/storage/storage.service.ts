import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  storeTokenData(token: string): void {
    sessionStorage.setItem("jwt", token);
 //   sessionStorage.setItem("refreshToken", refreshToken);
  }

  clearToken() {
    window.sessionStorage.setItem('jwt', "")
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

  getSubjectFromToken(): string {
    const jwtToken = window.sessionStorage.getItem('jwt')
    if (jwtToken) {
      const tokenSplit = jwtToken.split('.')
      const decoded = decodeURIComponent(encodeURIComponent(window.atob(tokenSplit[1])))
      const obj = JSON.parse(decoded)
      return obj.sub
    }
    return ""
  }

  getToken() {
    var dateFromJwt = this.getExpirationDateFromToken()
    if(!dateFromJwt) return ''
    const expDate = new Date(dateFromJwt * 1000)
    if (expDate < new Date()){
      this.clearToken()
      return ''
    }
    return sessionStorage.getItem("jwt")
  }
}
