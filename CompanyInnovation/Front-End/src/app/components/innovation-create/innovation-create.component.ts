import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {InnovationService} from "../../services/innovation/innovation.service";
import {StorageService} from "../../services/storage/storage.service";
import { AwsClientService } from 'src/app/services/aws-client/aws-client.service';
import {Innovation} from "../../model/innovation";

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

  async onSubmit() {
    let additionalParams = {
      //If there are query parameters or headers that need to be sent with the request you can add them here
      headers: {
        jwttoken : this.storageService.getToken()
      }
    }

    await this.awsClientService.sendRequest("/prod/submit", "POST",
    additionalParams,
    {
          "title": this.formGroup.get('title')?.value,
          "username": this.storageService.getUsernameFromToken(),
          "fullName": this.storageService.getFullNameFromToken(),
          "description": this.formGroup.get('description')?.value,
          "comment":'',
          "status":"PENDING",
          "userId": this.storageService.getSubjectFromToken()
        })

    this.router.navigate(['innovation-list']).then()
  }

}


