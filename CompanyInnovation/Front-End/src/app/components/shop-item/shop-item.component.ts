import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Product} from "../../model/product";
import {StorageService} from "../../services/storage/storage.service";
import {Router} from "@angular/router";
import {AwsClientService} from "../../services/aws-client/aws-client.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-shop-item',
  templateUrl: './shop-item.component.html',
  styleUrls: ['./shop-item.component.css']
})
export class ShopItemComponent implements OnInit{

  @Input() product: Product = new Product();
  @Output() buyItemEvent: EventEmitter<number> = new EventEmitter<number>();


  constructor(
    public storageService: StorageService,
    private router: Router,
    private awsClientService: AwsClientService,
    private _snackBar: MatSnackBar,
  ) {
  }

  ngOnInit() {
  }

  openEditView(product: Product) {
    this.router.navigate(['shop/edit-product', product.id], {state: {product}}).then();
  }

  async buyItem(product: Product) {

    let additionalParams = {
      headers: {
        jwttoken : this.storageService.getToken()
      }
    }

    await this.awsClientService.sendRequest("/prod/tokens", "POST",additionalParams,
      {
        "userId" : this.storageService.getSubjectFromToken(),
        "tokens": product.price
      }
      ) .then((result: any) =>{
        console.log(result.status)
        if (result.status == 201) {
          this.openSnackBar("Not enough tokens", "OK")
        }else {
          this.buyItemEvent.emit(product.price)
        }
      console.log(result)
    }).catch( function(result: any){
      console.log(result)
    });


  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action,  {
      duration: 3500,
      verticalPosition: "top",
    });
  }




}
