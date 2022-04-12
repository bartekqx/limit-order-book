import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Instrument, Order, OrderService } from 'src/app/services/order/order.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {

 instrument!:Instrument;
 orderType!: string;
 price!: number;
 quantity!: number;
 priceInvalid: boolean = false;

  constructor( private router:Router, private orderService:OrderService) {
    this.instrument = this.router.getCurrentNavigation()?.extras?.state?.['instrumentObj'];
  }

  ngOnInit() {

  }

  placeOrder() {
    if (this.price > this.instrument?.maxPrice ||
      this.price < this.instrument?.minPrice) {
        this.priceInvalid = true;
        return;
    }

    var order = <Order>{};
    order.instrument = this.instrument;
    order.orderType = this.orderType;
    order.price = this.price;
    order.quantity = this.quantity;

    console.log("REQ: " + JSON.stringify)
    const rsp = this.orderService.placeOrder(order);

    rsp.subscribe(rsp => {
      this.router.navigate(["/home"]);
    },
    err => {
    })
  }
}
