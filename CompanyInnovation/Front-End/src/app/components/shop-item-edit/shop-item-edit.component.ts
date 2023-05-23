import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/product";

@Component({
  selector: 'app-shop-item-edit',
  templateUrl: './shop-item-edit.component.html',
  styleUrls: ['./shop-item-edit.component.css']
})
export class ShopItemEditComponent implements OnInit{

  product: Product = new Product();

  ngOnInit(): void {
    this.product = history.state.product;
    console.log(JSON.stringify(this.product)) 
  }

}
