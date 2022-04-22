import { HttpClient } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { OrdersSeries } from '../order/order.service';

export interface Transaction {
  transactionId: string;
  order: ExecutedOrder;
}

export interface ExecutedOrder {
  id: string;
  instrumentName: string;
  userId: string;
  orderType: string;
  price: number;
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private zone: NgZone, private http:HttpClient) { }

  public getUserTransactions (): Observable<Transaction[]>  {

     return this.http
      .get<Transaction[]>("http://localhost:8901/transaction-service/transactions");
    }

  
  getExecutedOrders(): Observable<MessageEvent> {
    const eventSource = new EventSource("http://localhost:8901/transaction-service/transactions/series/executed-orders");
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

}
