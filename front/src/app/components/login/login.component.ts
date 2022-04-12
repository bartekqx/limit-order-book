import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SignInService } from 'src/app/services/sign-in/sign-in.service';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

   username:string = "";
   password:string = "";
   loginFail:boolean = false;
   message:any;

  constructor(
    private service:SignInService,
     private tokenStorageService:TokenStorageService,
     private router:Router) { }

  ngOnInit(): void {
  }

  doLogin() {
    const rsp = this.service.login(this.username, this.password);
    rsp.subscribe(rsp => {
      this.tokenStorageService.saveToken(rsp.accessToken);
      this.router.navigate(["/home"]);
    },
    err => {
      this.loginFail = true;
    })
  }
}
