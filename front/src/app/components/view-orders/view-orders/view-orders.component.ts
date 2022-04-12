import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Instrument, Order, OrderService } from 'src/app/services/order/order.service';

@Component({
  selector: 'app-view-orders',
  templateUrl: './view-orders.component.html',
  styleUrls: ['./view-orders.component.css']
})
export class ViewOrdersComponent implements OnInit {

  instrument!:Instrument;
  orders:Order[] = [];
  
   constructor( private router:Router, private orderService:OrderService) {
     this.instrument = this.router.getCurrentNavigation()?.extras?.state?.['instrumentObj'];
   }

   displayedColumns: string[] = ['id', 'userId', 'orderType', 'price', 'quantity'];
   
  ngOnInit(): void {
    if (this.instrument === undefined) {
      return;
    }
    this.orderService.getOrdersByCode(this.instrument.code).subscribe(res => {
        this.orders = res;
    }, err => {
      console.error("Error occured while fetching orders!");
    })
  }

}
