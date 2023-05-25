import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Innovation} from "../../model/innovation";
import { AwsClientService } from 'src/app/services/aws-client/aws-client.service';

@Component({
  selector: 'app-innovation-decline-comment',
  templateUrl: './innovation-decline-comment.component.html',
  styleUrls: ['./innovation-decline-comment.component.css']
})
export class InnovationDeclineCommentComponent implements OnInit{

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private awsClientService: AwsClientService
  ) { }

  innovation: Innovation = new Innovation()

  formGroup: FormGroup = new FormGroup({
    reasonForDeclining : new FormControl(''),
  });

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      reasonForDeclining: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), Validators.pattern('[-_a-zA-Z]*')]],
    });

    this.innovation = history.state.innovation;
    console.log(this.innovation)
  }

  async onSubmit(innovation: Innovation) {
    innovation.comment = this.formGroup.get('reasonForDeclining')?.value
    await this.awsClientService.sendRequest("/prod/acceptDeclineInnovation", "PUT",null,
    {
          "title": innovation.title,
          "username": innovation.username,
          "fullName": innovation.fullName,
          "description": innovation.description,
          "comment":innovation.comment,
          "status":innovation.status,
          "userId":innovation.userId,
          "id":innovation.id
        }) .then((result: any) =>{
          console.log(result)
        }).catch( function(result: any){
            console.log(result)
        });
    this.router.navigate(['innovation-list']).then()
  }
}
