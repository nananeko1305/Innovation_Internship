import {Component, OnInit} from '@angular/core';
import {Innovation} from "../../model/innovation";
import {ActivatedRoute, Router, RouterModule} from "@angular/router";

@Component({
  selector: 'app-innovation-accept-decline',
  templateUrl: './innovation-accept-decline.component.html',
  styleUrls: ['./innovation-accept-decline.component.css']
})
export class InnovationAcceptDeclineComponent implements OnInit{

  innovation: Innovation = new Innovation()
  innovation_id = String(this.route.snapshot.paramMap.get("id"))

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {

  }

  ngOnInit() {
    this.innovation = history.state.innovation;
    console.log(this.innovation)
  }

  Accept(innovation: Innovation) {
    this.router.navigate(['innovation-list'])
  }


  Decline(innovation: Innovation) {
    this.router.navigate(['innovationComment/', innovation.id], { state: {innovation} });
  }


}
