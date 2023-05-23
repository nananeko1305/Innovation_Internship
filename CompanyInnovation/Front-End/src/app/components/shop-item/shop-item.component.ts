import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {StorageService} from "../../services/storage/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-shop-item',
  templateUrl: './shop-item.component.html',
  styleUrls: ['./shop-item.component.css']
})
export class ShopItemComponent implements OnInit{

  product: Product = new Product();

  constructor(
    public storageService: StorageService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.product.id = "test"
    this.product.price = 15;
    this.product.title = "Test title"
    this.product.description = "Test description"
    this.product.image = "assets/images/CUP.jpeg"
  }

  openEditView(product: Product) {
    this.router.navigate(['shop/edit-product', product.id], {state: {product}}).then();
  }

  buyItem(product: Product) {
    console.log(JSON.stringify(product))

  }

}
