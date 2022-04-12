import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router:Router, private tokenStorage:TokenStorageService) { }

  ngOnInit(): void {
    if(!(this.tokenStorage.isLoggedIn())) {
      this.router.navigate(["/login"]);
    }
  }

}
