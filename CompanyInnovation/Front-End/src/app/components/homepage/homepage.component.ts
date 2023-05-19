import {Component, OnInit} from '@angular/core';
import {InnovationService} from "../../services/innovation/innovation.service";
import {Innovation} from "../../model/innovation";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit{

  innovations: Innovation[] = [];

  constructor(
    private innovationService: InnovationService
  ) {
  }

  ngOnInit() {
    this.innovationService.getInnovations()
      .subscribe(
      {
        next: (innovationsResponse: Innovation[]) => {
            this.innovations = innovationsResponse
          console.log(this.innovations)
        },
        error: (error: Error) => {
          console.log(error)
        }
      }
    )
  }


}
