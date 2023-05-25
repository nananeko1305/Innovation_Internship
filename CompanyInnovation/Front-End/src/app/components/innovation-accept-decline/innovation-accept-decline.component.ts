import {Component, OnInit} from '@angular/core';
import {Innovation} from "../../model/innovation";
import {ActivatedRoute, Router} from "@angular/router";
import { AwsClientService } from 'src/app/services/aws-client/aws-client.service';

@Component({
  selector: 'app-innovation-accept-decline',
  templateUrl: './innovation-accept-decline.component.html',
  styleUrls: ['./innovation-accept-decline.component.css']
})
export class InnovationAcceptDeclineComponent implements OnInit{

  innovation: Innovation = new Innovation()

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private awsClientService: AwsClientService
  ) {

  }

  ngOnInit() {
    this.innovation = history.state.innovation;
  }

  Accept(innovation: Innovation) {
    innovation.status = "APPROVED"

    this.awsClientService.sendRequest("/prod/acceptDeclineInnovation", "PUT", null
    ,{
      "id":innovation.id,
          "title": innovation.title,
          "username": innovation.username,
          "fullName": innovation.fullName,
          "description": innovation.description,
          "comment": "",
          "status": innovation.status,
          "userId":innovation.userId,
        }).then((result: any) =>{

      console.log(result)
     this.router.navigate(['innovation-list']).then()
  }).catch( function(result: Error){
    console.log(result)
    console.log("Error")
  });

  }

  Decline(innovation: Innovation) {
    innovation.status = "DECLINED"
    this.router.navigate(['innovationComment/', innovation.id], {state: {innovation}}).then();
  }


}
