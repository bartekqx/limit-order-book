import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Instrument {
  code: string;
  name: string;
  minPrice: number;
  maxPrice: number;
}

export interface Order {
  instrument: Instrument;
  orderType: string;
  price: number;
  quantity: number;
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

  getOrdersByCode(instrumentCode: string): Observable<Order[]> {
    return this.http.get<Order[]>("http://localhost:8901/order-service/orders/" + instrumentCode);
  }

  placeOrder(order:Order) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    return this.http.post("http://localhost:8901/order-service/orders", order, httpOptions);
 }
}
