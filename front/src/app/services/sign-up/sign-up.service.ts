import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SignUpService {

  constructor(private http:HttpClient) { }

  public register (username:string, password:string) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
     const requestDto:string = '{ "username":"' + username + '", "password": "' + password + '"}';

     return this.http
      .post("http://localhost:8901/user-service/sign-up", requestDto, httpOptions);
    }
}
