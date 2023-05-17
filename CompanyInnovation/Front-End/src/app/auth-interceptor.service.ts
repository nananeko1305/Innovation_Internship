import { HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './services/storage/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService {

  constructor(private storageService: StorageService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.storageService.getToken()
    const expDate = new Date(this.storageService.getExpirationDateFromToken() * 1000)
    if(req.headers.get('anonymous')){
      return next.handle(req)
    }
    if (token) {
      if (expDate < new Date()) {
          this.storageService.clearToken();
      }
      else {
        req = req.clone({
          setHeaders: { Authorization: `Bearer ${token}` }
        });
      }
    }
    return next.handle(req)
  }
}
