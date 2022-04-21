import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  constructor(private http:HttpClient) { }

  public getUserTransactions (): Observable<Transaction[]>  {

     return this.http
      .get<Transaction[]>("http://localhost:8901/transaction-service/transactions");
    }

  
    getExecutedOrders(interval: number): Observable<OrdersSeries[]> {
      return this.http.get<OrdersSeries[]>("http://localhost:8901/transaction-service/transactions/series/executed-orders/" + interval);
    }
  

}
