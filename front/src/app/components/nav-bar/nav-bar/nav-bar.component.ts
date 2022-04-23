import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogoutService } from 'src/app/services/logout/logout.service';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  private loggedIn: boolean;
  
  constructor(private router:Router, 
    private tokenStorage:TokenStorageService,
     private logoutService:LogoutService) {
    this.loggedIn = false;
   }

  ngOnInit(): void {
    if (this.tokenStorage.isLoggedIn() &&
      (window.location.pathname != '/login') &&
      (window.location.pathname != '/register')) {
      this.loggedIn = true;
    }
  }

  logout() {
    this.logoutService.logout().subscribe(res => {
      this.tokenStorage.clear();
    }, err => {
      this.tokenStorage.clear();
    })

    this.router.navigate(["/login"])

  }

  home() {
    this.router.navigate(["/home"])
    .then(() => {
      window.location.reload();
    });
  }

  analysis() {
    this.router.navigate(["/analysis"])
    .then(() => {
      window.location.reload();
    });
  }

  myProfile() {
    this.router.navigate(["/my-profile"])
    .then(() => {
      window.location.reload();
    });
  }

  get isLoggedIn(): boolean {
    return this.loggedIn;
  }
}
