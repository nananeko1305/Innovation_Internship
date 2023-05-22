import {Component, OnInit} from '@angular/core';
import {InnovationService} from "../../services/innovation/innovation.service";
import {Innovation} from "../../model/innovation";
import { AwsClientService } from 'src/app/services/aws-client/aws-client.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit{

  innovations: Innovation[] = [];

  constructor(
    private innovationService: InnovationService,
    private awsClientService: AwsClientService
  ) {
  }

  ngOnInit() {
    // this.innovationService.getInnovations()
    //   .subscribe(
    //   {
    //     next: (innovationsResponse: Innovation[]) => {
    //         this.innovations = innovationsResponse
    //       console.log(this.innovations)
    //     },
    //     error: (error: Error) => {
    //       console.log(error)
    //     }
    //   }
    // )
    this.awsClientService.sendRequest("/prod/innovations", "GET") .then((result: any) =>{
      console.log(result)
      this.innovations = result.data
  }).catch( function(result: any){
      console.log(result)
  });
  }


}
