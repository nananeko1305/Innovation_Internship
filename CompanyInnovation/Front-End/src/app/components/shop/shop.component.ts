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

  onBuyItemEvent(price: number): void {
    this.userTokens.tokens = this.userTokens.tokens - price;
  }

  constructor(
    public storageService: StorageService,
    private awsClientService: AwsClientService,
  ) {
  }

  ngOnInit(): void {

    let additionalParams = {
      //If there are query parameters or headers that need to be sent with the request you can add them here
      headers: {
        jwttoken : this.storageService.getToken()
      }
    }

    if(this.storageService.getRoleFromToken()=='Employee'){
      this.awsClientService.sendRequest("/prod/tokens", "GET",additionalParams, null) .then((result: any) =>{
        this.userTokens = result.data
      }).catch( function(result: any){
        console.log(result)
      });
    }
  }
}
