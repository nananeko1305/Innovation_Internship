import {Component, OnInit} from '@angular/core';
import {Innovation} from "../../model/innovation";
import {StorageService} from "../../services/storage/storage.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit{

  innovations: Innovation[] = [];

  constructor(
    public storageService: StorageService,
  ) {
  }

  ngOnInit() {
  }


}
