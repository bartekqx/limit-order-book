import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SignInService } from 'src/app/services/sign-in.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

   username:string;
   password:string;
   message:any;

  constructor(private service:SignInService, private router:Router) { }

  ngOnInit(): void {
  }

  doLogin() {
    const rsp = this.service.login(this.username, this.password);
    rsp.subscribe(rsp => {
      localStorage.setItem('accessToken', rsp.accessToken);
      this.router.navigate(["/home"]);
    })
  }
}
