import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { saveAs } from 'file-saver';
import {FormControl, FormGroup} from "@angular/forms";
import {Product} from "../../model/product";
import {AwsClientService} from "../../services/aws-client/aws-client.service";
import {Innovation} from "../../model/innovation";
import {StorageService} from "../../services/storage/storage.service";

@Component({
  selector: 'app-shop-item-create',
  templateUrl: './shop-item-create.component.html',
  styleUrls: ['./shop-item-create.component.css']
})
export class ShopItemCreateComponent implements OnInit{

  constructor(
    private http: HttpClient,
    private awsClientService: AwsClientService,
    private storageService: StorageService
  ) {
  }

  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  formGroup: FormGroup = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    price: new FormControl(''),
  });


  ngOnInit(): void {

  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0] as File;
    this.previewImage();
  }

  previewImage() {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.imagePreview = event.target.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  addItem() {
    let additionalParams = {
      //If there are query parameters or headers that need to be sent with the request you can add them here
      headers: {
        jwttoken : this.storageService.getToken()
      }
    }

    this.awsClientService.sendRequest("/prod/product", "POST",
      additionalParams,
      {
        "title": this.formGroup.get('title')?.value,
        "description": this.formGroup.get('description')?.value,
        "price": this.formGroup.get('price')?.value,
        "image": "assets/images/" + this.selectedFile?.name
      }) .then(function(result: Innovation){
      console.log(result)
    }).catch( function(result: any){
      console.log(result)
    });

  }



}
