import {Component, OnInit} from '@angular/core';
import {Innovation} from "../../model/innovation";
import {ActivatedRoute, Router, RouterModule} from "@angular/router";
import {InnovationService} from "../../services/innovation/innovation.service";

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
    private router: Router,
    private innovationService: InnovationService,
  ) {

  }

  ngOnInit() {
    this.innovation = history.state.innovation;
    console.log(this.innovation)
  }

  Accept(innovation: Innovation) {
    innovation.status = "APPROVED"
    this.innovationService.approveOrDecline(innovation).subscribe(
      {
        next : (innovation) => {
          console.log(innovation)
          this.router.navigate(['innovation-list'])
    },
        error : (error) => {
          console.log(error)
        }
      }
    )

  }

  Decline(innovation: Innovation) {
    innovation.status = "DECLINED"
    this.router.navigate(['innovationComment/', innovation.id], {state: {innovation}});
  }


}
