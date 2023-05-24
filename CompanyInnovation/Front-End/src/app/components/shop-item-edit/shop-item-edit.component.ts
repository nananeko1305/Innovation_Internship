import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {Innovation} from "../../model/innovation";
import {AwsClientService} from "../../services/aws-client/aws-client.service";
import {Router} from "@angular/router";
import {StorageService} from "../../services/storage/storage.service";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-shop-item-edit',
  templateUrl: './shop-item-edit.component.html',
  styleUrls: ['./shop-item-edit.component.css']
})
export class ShopItemEditComponent implements OnInit{

  product: Product = new Product();

  constructor(
    private awsClientService: AwsClientService,
    private router: Router,
    private storageService: StorageService,
    private formBuilder: FormBuilder,

  ) {
  }

  formGroup: FormGroup = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    price: new FormControl(''),
  });


  ngOnInit(): void {
    this.product = history.state.product;
    console.log(JSON.stringify(this.product))
    this.formGroup = this.formBuilder.group({
      title: [this.product.title],
      description: [this.product.description],
      price: [this.product.price]
    });
  }

  async editProduct() {
    let additionalParams = {
      headers: {
        jwttoken : this.storageService.getToken()
      }
    }
    this.product.title = this.formGroup.get('title')?.value;
    this.product.description = this.formGroup.get('description')?.value;
    this.product.price = this.formGroup.get('price')?.value

    this.awsClientService.sendRequest("/prod/product", "PUT",
      additionalParams,
      {
        "id": this.product.id,
        "title": this.product.title,
        "description": this.product.description,
        "price": this.product.price,
        "image": this.product.image,
      }) .then(function(result: Innovation){
      console.log(result)
    }).catch( function(result: any){
      console.log(result)
    });

    await this.delay(500);


    this.router.navigate(['/shop']).then()
  }

  async deleteProduct() {
    let additionalParams = {
      headers: {
        jwttoken : this.storageService.getToken()
      }
    }

    this.awsClientService.sendRequest("/prod/product", "DELETE",
      additionalParams,
      {
        "id": this.product.id,
        "title": this.product.title,
        "description": this.product.description,
        "price": this.product.price,
        "image": this.product.image,
      }) .then(function(result: Innovation){
      console.log(result)
    }).catch( function(result: any){
      console.log(result)
    });

    await this.delay(500);

    this.router.navigate(['/shop']).then()
  }

  delay(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

}
