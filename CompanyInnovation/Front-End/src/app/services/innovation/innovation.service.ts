import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Innovation} from "../../model/innovation";
import {Observable} from "rxjs";
import { AwsClientService } from '../aws-client/aws-client.service';

@Injectable({
  providedIn: 'root'
})
export class InnovationService {

  constructor(
    private httpClient : HttpClient,
    private awsClientService : AwsClientService
  ) { }

  public createPost(innovation: Innovation): Observable<any>{
    return this.httpClient.post<Innovation>("http://localhost:8080/submit", innovation)
  }

  public getInnovations(): Observable<Innovation[]>{
    return this.httpClient.get<Innovation[]>("http://localhost:8080/innovations")
  }

  public approveOrDecline(innovation: Innovation): Observable<Innovation>{
    return this.httpClient.put<Innovation>("http://localhost:8080/acceptDeclineInnovation", innovation)
  }

}



