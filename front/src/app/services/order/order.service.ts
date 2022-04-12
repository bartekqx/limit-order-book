import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Instrument {
  code: string;
  name: string;
  minPrice: number;
  maxPrice: number;
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http:HttpClient) {

   }

  getInstruments(): Observable<Instrument[]> {
     return this.http.get<Instrument[]>("http://localhost:8901/order-service/instruments");
  }
}
