import {Component, Input, OnInit} from '@angular/core';
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

  buyItem(product: Product) {

    let additionalParams = {
      //If there are query parameters or headers that need to be sent with the request you can add them here
      headers: {
        jwttoken : this.storageService.getToken()
      }
    }

    this.awsClientService.sendRequest2("/tokens", "POST",additionalParams,
      {
        "userId" : this.storageService.getSubjectFromToken(),
        "tokens": product.price
      }
      ) .then((result: any) =>{
        console.log(result.status)
        if (result.status == 201) {
          this.openSnackBar("Not enough tokens", "OK")
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
