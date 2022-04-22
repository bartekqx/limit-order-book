import { Component, OnInit } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { OrderService} from 'src/app/services/order/order.service';
import { OrdersSeries} from 'src/app/services/order/order.service';
import { TransactionService } from 'src/app/services/transaction/transaction-service';
import {multi} from './data';



@Component({
  selector: 'app-analysis',
  templateUrl: './analysis.component.html',
  styleUrls: ['./analysis.component.css']
})
export class AnalysisComponent implements OnInit {

  interval:number = 5;

  constructor(private orderService:OrderService, private transactionService:TransactionService) {

  }

  pendingOrders: OrdersSeries[] = [] ;
  executedOrders: OrdersSeries[] = [] ;
  view: [number, number] = [1000,500];

  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Time [' + this.interval + ' min]';
  pendingOrders_yAxisLabel: string = 'Pending Orders';
  executedOrders_yAxisLabel: string = 'Executed  Orders';
  timeline: boolean = true;
  colorScheme: Color = {
    name: 'myScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#704FC4', '#4B852C', '#B67A3D', '#5B6FC8', '#25706F']
  };
  

  onSelect(event: any) {
    console.log(event);
  }

  onActivate(data: any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data: any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

  ngOnInit(): void {
    this.orderService.getPendingOrders(this.interval).subscribe(ordersSeries => {
      for (const pendingOrder of ordersSeries) {
        for(const series of pendingOrder.series) {
          const epochMilis = series.name;
          series.name = new Date(epochMilis);
        }
        this.pendingOrders.push(pendingOrder)
      }

      this.pendingOrders = [...this.pendingOrders];
      
      this.transactionService.getExecutedOrders(this.interval).subscribe(ordersSeries => {
        for (const executedOrder of ordersSeries) {
          for(const series of executedOrder.series) {
            const epochMilis = series.name;
            series.name = new Date(epochMilis);
          }
          this.executedOrders.push(executedOrder)
        }
  
        this.executedOrders = [...this.executedOrders];
    })
    })
  }

  xAxisFormat(val : any) {
    if (val % 1 > 0) return "";
    return val ;
 }
}
