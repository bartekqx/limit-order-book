import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/services/order/order.service';
import { Transaction, TransactionService } from 'src/app/services/transaction/transaction-service';
import { User, UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  constructor(private transactionService:TransactionService, private userService:UserService) { }

  transactions:Transaction[] = [];
  user?: User;
  displayedColumns: string[] = ['id', 'orderId', 'orderType', 'price', 'quantity'];
   
  ngOnInit(): void {
   this.transactionService.getUserTransactions().subscribe(res => {
    this.transactions = res;
   },
   err => {
     console.error("Error while fetching transactions for user!");
   });

   this.userService.getUserDetails().subscribe(res => {
    this.user = res;
   },
   err => {
     console.error("Error while fetching user details!");
   });

  }

  changePassword() {

  }
}
