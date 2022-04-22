import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
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

export interface Orders {
  askOrders: Order[];
  bidOrders: Order[];
}

export interface OrdersSeries {
  name: string;
  series: OrdersSeriesXY[];
}

export interface OrdersSeriesXY {
  name: Date;
  value: number
}

export interface OrderSeriesDto {
  instrumentName: string;
  timestamp: number
  counter: number;
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private zone: NgZone, private http:HttpClient) {

   }

  getInstruments(): Observable<Instrument[]> {
     return this.http.get<Instrument[]>("http://localhost:8901/order-service/instruments");
  }

  getOrdersByName(instrumentName: string): Observable<Orders> {
    return this.http.get<Orders>("http://localhost:8901/order-service/orders/" + instrumentName);
  }

  getPendingOrders(): Observable<MessageEvent> {
    const eventSource = new EventSource("http://localhost:8901/order-service/orders/series/pending-orders");
    return new Observable(observer => {

      eventSource.onmessage = event => {
          this.zone.run(() => {
            observer.next(event);
          });
      };

      eventSource.onerror = error => {
        this.zone.run(() => {
          observer.error(error);
        });
    };
    })
  }

  placeOrder(order:Order) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    return this.http.post("http://localhost:8901/order-service/orders", order, httpOptions);
 }
}
