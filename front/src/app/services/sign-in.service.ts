import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export interface LoginResponse {
  accessToken: string
}


@Injectable({
  providedIn: 'root'
})
export class SignInService {


  constructor(private http:HttpClient) {
    
   }

   public login (username:string, password:string) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
     const requestDto:string = '{ "username":"' + username + '", "password": "' + password + '"}';

     return this.http
      .post<LoginResponse>("http://localhost:8901/user-service/sign-in", requestDto, httpOptions);
    }
}
