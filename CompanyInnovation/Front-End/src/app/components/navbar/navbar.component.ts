import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/services/storage/storage.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private router: Router, public storageService : StorageService){

  }

  isLoggedIn() {
    return this.storageService.getToken() !== ""
  }

  logout(){
    this.storageService.clearToken()
    this.router.navigateByUrl("login")
  }

}
