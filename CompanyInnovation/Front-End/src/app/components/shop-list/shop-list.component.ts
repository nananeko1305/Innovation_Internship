import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Product} from "../../model/product";
import {AwsClientService} from "../../services/aws-client/aws-client.service";

@Component({
  selector: 'app-shop-list',
  templateUrl: './shop-list.component.html',
  styleUrls: ['./shop-list.component.css']
})
export class ShopListComponent implements OnInit{

  products: Product[] = []
  tokens: number = 0
  @Output() buyItemEvent: EventEmitter<number> = new EventEmitter<number>();

  constructor(
    private awsClientService: AwsClientService,
  ) {
  }

  ngOnInit(): void {

    this.awsClientService.sendRequest("/prod/product", "GET",null) .then((result: any) =>{
      this.products = result.data
    }).catch( function(result: any){
      console.log(result)
    });
  }

  onBuyItemEvent(price: number): void {
    this.buyItemEvent.emit(price);
  }

}
