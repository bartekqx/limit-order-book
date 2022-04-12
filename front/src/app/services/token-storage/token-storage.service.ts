import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  private accessTokenKey = "accessToken";

  private loggedIn: boolean;
  
  constructor() {
    this.loggedIn = false;
  }

  isLoggedIn():boolean {
    return localStorage.getItem(this.accessTokenKey) != null;
  }

  saveToken(token:string) {
    localStorage.removeItem(this.accessTokenKey);
    localStorage.setItem(this.accessTokenKey, token);
    this.loggedIn = true;
  }

  clear() {
    localStorage.removeItem(this.accessTokenKey);
    this.loggedIn = false;
  }
}
