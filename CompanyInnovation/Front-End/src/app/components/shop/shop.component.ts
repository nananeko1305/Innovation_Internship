import {Component, OnInit} from '@angular/core';
import {StorageService} from "../../services/storage/storage.service";
import {AwsClientService} from "../../services/aws-client/aws-client.service";
import {UserTokens} from "../../model/userTokens";

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit{

  userTokens: UserTokens = new UserTokens()

  constructor(
    public storageService: StorageService,
    private awsClientService: AwsClientService,
  ) {
  }

  ngOnInit(): void {
    this.awsClientService.sendRequest2("/tokens", "GET",null) .then((result: any) =>{
      this.userTokens = result.data
    }).catch( function(result: any){
      console.log(result)
    });
  }

}
