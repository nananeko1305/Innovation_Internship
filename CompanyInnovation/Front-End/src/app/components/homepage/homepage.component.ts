import {Component, OnInit} from '@angular/core';
import {Innovation} from "../../model/innovation";
import { AwsClientService } from 'src/app/services/aws-client/aws-client.service';
import {StorageService} from "../../services/storage/storage.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit{

  innovations: Innovation[] = [];

  constructor(
    private awsClientService: AwsClientService,
    public storageService: StorageService,
  ) {
  }

  ngOnInit() {
    this.awsClientService.sendRequest("/prod/innovations","GET",null) .then((result: any) =>{
      this.innovations = result.data
  }).catch( function(result: any){
      console.log(result)
  });
  }


}
