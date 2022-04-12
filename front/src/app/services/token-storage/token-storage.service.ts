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

  getToken(): string {
    const token = localStorage.getItem(this.accessTokenKey);
    
    if (token == null) {
      return '';
    }

    return token;
  }

  clear() {
    localStorage.removeItem(this.accessTokenKey);
    this.loggedIn = false;
  }
}
