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

    // const innovation = new Innovation();
    // innovation.title = this.formGroup.get('title')?.value
    // innovation.description = this.formGroup.get('description')?.value
    // innovation.userId = this.storageService.getSubjectFromToken()
    // console.log(this.storageService.getUsernameFromToken())
    // innovation.username = this.storageService.getUsernameFromToken()
    // innovation.status = "PENDING"

    this.awsClientService.sendRequest("/prod/submit", "POST", {
          "title": this.formGroup.get('title')?.value,
          "username": this.storageService.getUsernameFromToken(),
          "fullName": "TestFullName",
          "description": this.formGroup.get('description')?.value,
          "comment":'',
          "status":"PENDING"
        }) .then(function(result: any){
      console.log(result)
  }).catch( function(result: any){
      console.log(result)
  });

  }

}


