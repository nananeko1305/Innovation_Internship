import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Innovation} from "../../model/innovation";

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
  ) { }

  innovation: Innovation = new Innovation()
  innovation_id = String(this.route.snapshot.paramMap.get("id"))

  formGroup: FormGroup = new FormGroup({
    reasonForDeclining : new FormControl(''),
  });

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      reasonForDeclining: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30), Validators.pattern('[-_a-zA-Z]*')]],
    });

    //get an innovation from state
    this.innovation = history.state.innovation;
    console.log(this.innovation)
  }

  onSubmit() {
    this.router.navigate(['innovation-list'])
  }

}
