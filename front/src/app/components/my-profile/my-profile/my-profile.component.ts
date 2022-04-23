import { DatePipe } from '@angular/common';
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
  displayedColumns: string[] = ['id', 'creationTime', 'orderId', 'orderType', 'price', 'quantity'];
   
  ngOnInit(): void {
   this.transactionService.getUserTransactions().subscribe(res => {
     for (const transaction of res) {
       transaction.createdTime = new Date(transaction.createdTime).toLocaleString();
     }
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

  setDateTime(dateTime) {
    let pipe = new DatePipe('en-US');

    const time = pipe.transform(dateTime, 'mediumTime', 'UTC');

    const date = pipe.transform(dateTime, 'MM/dd/yyyy', 'UTC');

    return date + ' ' + time;
  }
  
  changePassword() {

  }
}
