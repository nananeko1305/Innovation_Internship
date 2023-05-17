import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Innovation} from "../../model/innovation";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class InnovationService {

  constructor(
    private httpClient : HttpClient
  ) { }

  // public createPost(innovation: Innovation): Observable<Innovation>{
  //   return this.httpClient.post<Innovation>("https://5j10nowhj2.execute-api.eu-north-1.amazonaws.com/prod/submit", innovation)
  // }

  public createPost(innovation: Innovation): Observable<Innovation>{
    return this.httpClient.post<Innovation>("http://localhost:8080/submit", innovation)
  }

  public getInnovations(): Observable<Innovation[]>{
    return this.httpClient.get<Innovation[]>("http://localhost:8081/innovations")
  }

}



