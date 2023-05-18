import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {InnovationService} from "../../services/innovation/innovation.service";
import {Innovation} from "../../model/innovation";
import {StorageService} from "../../services/storage/storage.service";
import {HttpErrorResponse} from "@angular/common/http";
import { AwsClientService } from 'src/app/services/aws-client/aws-client.service';

@Component({
  selector: 'app-innovation-create',
  templateUrl: './innovation-create.component.html',
  styleUrls: ['./innovation-create.component.css']
})
export class InnovationCreateComponent implements OnInit{

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private innovationService: InnovationService,
    private storageService: StorageService,
    private awsClientService: AwsClientService
  ) { }

  formGroup: FormGroup = new FormGroup({
    title : new FormControl(''),
    description: new FormControl('')
  });

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), Validators.pattern('[-_a-zA-Z0-9]*')]],
      description: ['', [Validators.required, Validators.minLength(3), Validators.pattern('[-0-9]*')]]
    });
  }

  onSubmit() {

    const innovation = new Innovation();
    innovation.title = this.formGroup.get('title')?.value
    innovation.description = this.formGroup.get('description')?.value
    innovation.userId = this.storageService.getSubjectFromToken()
    console.log(this.storageService.getUsernameFromToken())
    innovation.username = this.storageService.getUsernameFromToken()
    innovation.status = "PENDING"

    // this.innovationService.createPost(innovation).subscribe(
    //   {
    //     next: (innovation: Innovation) => {
    //       console.log(JSON.stringify(innovation))
    //       console.log("Success")
    //     },
    //     error: (error: HttpErrorResponse) => {
    //       console.log(error)
    //     }
    //   }
    // );

    var apigClientFactory = require('aws-api-gateway-client').default;

    var apigClient = apigClientFactory.newClient({
      invokeUrl:'https://5j10nowhj2.execute-api.eu-north-1.amazonaws.com',
      accessKey: this.storageService.getAccessKey(), //'ACCESS_KEY',
      secretKey: this.storageService.getSecretKey(), //'SECRET_KEY',
      sessionToken: this.storageService.getSessionToken(), // 'SESSION_TOKEN', //OPTIONAL: If you are using temporary credentials you must include the session token
      region: 'eu-north-1' // OPTIONAL: The region where the API is deployed, by default this parameter is set to us-east-1
    });

    
    apigClient.invokeApi({}, '/submit', 'POST', {}, {
      "title": this.formGroup.get('title')?.value,
      "username": this.storageService.getUsernameFromToken(),
      "fullName": "TestFullName",
      "description": this.formGroup.get('description')?.value,
      "comment":null,
      "status":"APPROVED"
    }).then(function(result: any){
      //This is where you would put a success callback
      console.log(result)
  }).catch( function(result: any){
      //This is where you would put an error callback
      console.log(result)
  });

  //   this.awsClientService.sendRequest("/submit", "POST", {}) .then(function(result: any){
  //     //This is where you would put a success callback
  //     console.log(result)
  // }).catch( function(result: any){
  //     //This is where you would put an error callback
  //     console.log(result)
  // });

  }

}


