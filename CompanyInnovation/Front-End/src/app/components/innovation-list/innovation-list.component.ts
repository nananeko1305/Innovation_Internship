import {Component, Input, OnInit} from '@angular/core';
import {Innovation} from "../../model/innovation";
import { AwsClientService } from 'src/app/services/aws-client/aws-client.service';

@Component({
  selector: 'app-innovation-list',
  templateUrl: './innovation-list.component.html',
  styleUrls: ['./innovation-list.component.css']
})
export class InnovationListComponent implements OnInit{

  @Input() innovations: Innovation[] = []

  constructor(
    private awsClientService: AwsClientService
  ) {}


  ngOnInit() {
    this.awsClientService.sendRequest("/prod/innovations", "GET",null) .then((result: any) =>{
      console.log(result)
      this.innovations = result.data
  }).catch( function(result: any){
      console.log(result)
  });
  }

}
