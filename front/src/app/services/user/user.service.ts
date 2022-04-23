import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  phoneNumber: number;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  public getUserDetails (): Observable<User>  {

     return this.http
      .get<User>("http://localhost:8901/user-service/users/details");
    }

}
