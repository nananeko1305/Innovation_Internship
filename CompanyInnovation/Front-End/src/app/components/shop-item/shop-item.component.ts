import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {StorageService} from "../../services/storage/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-shop-item',
  templateUrl: './shop-item.component.html',
  styleUrls: ['./shop-item.component.css']
})
export class ShopItemComponent implements OnInit{

  @Input() product: Product = new Product();

  constructor(
    public storageService: StorageService,
    private router: Router,
  ) {
  }

  ngOnInit() {
  }

  openEditView(product: Product) {
    this.router.navigate(['shop/edit-product', product.id], {state: {product}}).then();
  }

  buyItem(product: Product) {
    console.log(JSON.stringify(product))

  }

}
