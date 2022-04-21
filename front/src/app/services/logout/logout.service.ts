import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  constructor(private http:HttpClient) {
    
  }

  public logout () {
    return this.http
     .post("http://localhost:8901/user-service/logout", null);
   }
}
