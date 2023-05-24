import {Component, OnInit} from '@angular/core';
import {StorageService} from "../../services/storage/storage.service";

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent{


  constructor(
    public storageService: StorageService,
  ) {
  }

}
