import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  private loggedIn: boolean;
  
  constructor(private router:Router, private tokenStorage:TokenStorageService) {
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
    this.tokenStorage.clear();
    this.router.navigate(["/login"])
  }

  get isLoggedIn(): boolean {
    return this.loggedIn;
  }
}
