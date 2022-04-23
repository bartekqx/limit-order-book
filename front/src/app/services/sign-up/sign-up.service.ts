import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

export interface SignUpDto {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  phone?: number;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class SignUpService {

  constructor(private http:HttpClient) { }

  public register (signUpDto:SignUpDto) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    
     return this.http
      .post("http://localhost:8901/user-service/sign-up", signUpDto, httpOptions);
    }
}
