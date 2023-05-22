import {Component, Input, OnInit} from '@angular/core';
import {Innovation} from "../../model/innovation";
import {Router} from "@angular/router";
import {StorageService} from "../../services/storage/storage.service";

@Component({
  selector: 'app-innovation-view',
  templateUrl: './innovation-view.component.html',
  styleUrls: ['./innovation-view.component.css']
})
export class InnovationViewComponent implements OnInit{

  @Input() innovation: Innovation = new Innovation()

  constructor(
    private router: Router,
    public storageService: StorageService,
  ) {

  }


  ngOnInit() {

  }

  ShowInnovation(innovation: Innovation): void {
    this.router.navigate(['innovationAcceptDecline/', innovation.id], { state: {innovation} }).then();
  }

}
