import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {Innovation} from "../../model/innovation";
import {AwsClientService} from "../../services/aws-client/aws-client.service";
import {StorageService} from "../../services/storage/storage.service";

@Component({
  selector: 'app-shop-list',
  templateUrl: './shop-list.component.html',
  styleUrls: ['./shop-list.component.css']
})
export class ShopListComponent implements OnInit{

  constructor(
    private awsClientService: AwsClientService,
    private storageService: StorageService,
  ) {
  }

  products: Product[] = []
  ngOnInit(): void {
    let additionalParams = {
      //If there are query parameters or headers that need to be sent with the request you can add them here
      headers: {
        jwttoken : this.storageService.getToken(),
      }
    }
    this.awsClientService.sendRequest("/prod/product", "GET",
      additionalParams,
      {
      }) .then(function(result: Product[]){
      console.log(JSON.stringify(result))

    }).catch( function(result: any){
      console.log(result)
    });
  }

}
