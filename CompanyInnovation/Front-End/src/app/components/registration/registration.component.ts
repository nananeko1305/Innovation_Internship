import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { CognitoService } from 'src/app/services/cognito/cognito.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit{

  userNotSignedUp = true

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private cognitoService: CognitoService
  ) {
  }

  formGroup: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    email: new FormControl(''),
    fullName: new FormControl('')
  });

  formGroupConfirmation: FormGroup = new FormGroup({
    code: new FormControl('')
  });

  onSubmit(){
        this.cognitoService.signUp(this.formGroup.get('username')?.value,this.formGroup.get('password')?.value, this.formGroup.get('email')?.value, this.formGroup.get('fullName')?.value).then(() => {
              alert("Success.")
              this.userNotSignedUp = false
        })
        .catch((error:any) => {
          console.log(error)
          alert("Error.")
        })
  }

  onSubmitCode(){
      this.cognitoService.confirmSignUp(this.formGroup.get('username')?.value, this.formGroupConfirmation.get('code')?.value)
      .then(() => {
        alert("Success.")
        this.router.navigateByUrl('login').then()
      })
      .catch((error: any) => {
        console.log(error)
        alert("Error.")
      })
  }

  ngOnInit(): void {
    this.userNotSignedUp = true
    this.formGroup = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), Validators.pattern('[-_a-zA-Z0-9]*')]],
      password: ['', [Validators.required, Validators.minLength(3), Validators.pattern('[-0-9]*')]],
      email: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), Validators.pattern('[-_a-zA-Z0-9]*')]],
      fullName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), Validators.pattern('[-_a-zA-Z0-9]*')]],
    });
  }
}
