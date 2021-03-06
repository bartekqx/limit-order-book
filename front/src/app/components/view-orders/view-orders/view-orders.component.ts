import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Instrument, Order, OrderService } from 'src/app/services/order/order.service';

@Component({
  selector: 'app-view-orders',
  templateUrl: './view-orders.component.html',
  styleUrls: ['./view-orders.component.css']
})
export class ViewOrdersComponent implements OnInit {

  instrument:Instrument;
  bidOrders:Order[] = [];
  askOrders:Order[] = [];
  
   constructor( private router:Router, private orderService:OrderService) {
     this.instrument = this.router.getCurrentNavigation()?.extras?.state?.['instrumentObj'];
   }

   displayedColumns: string[] = ['id', 'userId', 'orderType', 'price', 'quantity'];
   
  ngOnInit(): void {
    if (this.instrument === undefined) {
      return;
    }

    this.orderService.getOrdersByName(this.instrument.name).subscribe(res => {
        this.bidOrders = res.bidOrders;
        this.askOrders = res.askOrders;
    }, err => {
      console.error("Error occured while fetching orders!");
    })
  }

}
