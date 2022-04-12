import { Component, OnInit } from '@angular/core';
import { SignUpService } from 'src/app/services/sign-up/sign-up.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private signUpService:SignUpService) { }

  username:string = "";
  password:string = "";
  confirmedPassword:string = "";
  passwordsNotMatch:boolean = false;
  usernameExists:boolean = false;
  registerSuccess:boolean = false;

  ngOnInit(): void {
    
  }

  doRegister() {
    if (this.passwordsNotMatching(this.password, this.confirmedPassword)) {
        this.passwordsNotMatch = true;
        return;
    }

    const rsp = this.signUpService.register(this.username, this.password)

    rsp.subscribe(rsp => {
      this.registerSuccess = true;
    },
    err => {
      this.registerSuccess = false;
      if (err.status == 400) {
        this.usernameExists = true;
      }
    })
  }

  passwordsNotMatching(pass1: string, pass2: string): boolean {
    return !(pass1 === pass2);
  }



}
