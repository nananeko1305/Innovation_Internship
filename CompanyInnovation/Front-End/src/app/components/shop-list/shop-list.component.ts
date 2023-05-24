import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {Innovation} from "../../model/innovation";
import {AwsClientService} from "../../services/aws-client/aws-client.service";
import {StorageService} from "../../services/storage/storage.service";
import {ProductService} from "../../services/product/product.service";

@Component({
  selector: 'app-shop-list',
  templateUrl: './shop-list.component.html',
  styleUrls: ['./shop-list.component.css']
})
export class ShopListComponent implements OnInit{

  products: Product[] = []
  tokens: number = 0

  constructor(
    private awsClientService: AwsClientService,
    private storageService: StorageService,
    private productService: ProductService,
  ) {
  }

  ngOnInit(): void {

    this.awsClientService.sendRequest("/prod/product", "GET",null) .then((result: any) =>{
      this.products = result.data
    }).catch( function(result: any){
      console.log(result)
    });
  }

}
