import {Component, OnInit} from '@angular/core';
import {Innovation} from "../../model/innovation";

@Component({
  selector: 'app-innovation-list',
  templateUrl: './innovation-list.component.html',
  styleUrls: ['./innovation-list.component.css']
})
export class InnovationListComponent implements OnInit{

  innovations: Innovation[] = []

  constructor() {
  }

  ngOnInit() {

    let innovation1 = new Innovation();
    innovation1.id = 1
    innovation1.title = "test1"
    innovation1.details = "details1"
    innovation1.username = "Mika"

    this.innovations.push(innovation1)

    let innovation2 = new Innovation();
    innovation1.id = 2
    innovation2.title = "test2"
    innovation2.details = "details2"
    innovation2.username = "Zika"


    this.innovations.push(innovation2)



  }

}
