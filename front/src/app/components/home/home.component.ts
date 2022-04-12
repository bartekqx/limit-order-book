import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Instrument, OrderService } from 'src/app/services/order/order.service';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private instruments:Instrument[] = [];

  constructor(private router:Router, 
    private orderService:OrderService,
    private tokenStorage:TokenStorageService) {

    }

  displayedColumns: string[] = ['code', 'name', 'minPrice', 'maxPrice', 'create-order', 'view-orders'];
  dataSource: Instrument[] = [];

  ngOnInit(): void {
    if(!(this.tokenStorage.isLoggedIn())) {
      this.router.navigate(["/login"]);
    }
    
    this.orderService.getInstruments()
      .subscribe(rsp => {
        this.instruments = rsp;
        this.dataSource = this.instruments;
      },
      err => {
        console.log("Error ocurred while ferching instruments!");
      })
  }

  viewOrders(instrument:Instrument) {
    this.router.navigate(["/login"])
  }

  createOrder() {
    this.router.navigate(["/login"])
  }

}
