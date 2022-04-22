import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { OrderSeriesDto, OrderService} from 'src/app/services/order/order.service';
import { OrdersSeries} from 'src/app/services/order/order.service';
import { OrdersSeriesXY} from 'src/app/services/order/order.service';
import { TransactionService } from 'src/app/services/transaction/transaction-service';


@Component({
  selector: 'app-executed-transactions-analysis',
  templateUrl: './executed-transactions-analysis.component.html',
  styleUrls: ['./executed-transactions-analysis.component.css']
})
export class ExecutedTransactionsAnalysisComponent implements OnInit {


  constructor(private router:Router, private orderService:OrderService, private transactionService:TransactionService) {
  }

  instrumentsNames: string[] = [] ;
  executedOrders: OrdersSeries[] = [] ;
  view: [number, number] = [1000,700];

  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Time';
  executedOrders_yAxisLabel: string = 'Executed Transactions';
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
    this.initSeries();

    this.transactionService.getExecutedOrders().subscribe(event => {
      let ordersSeriesJSON = JSON.parse(event.data); 
      let executedTransactions = <OrderSeriesDto>ordersSeriesJSON;

      this.setExecutedOrderSeries(executedTransactions);
    })
  }

  private initSeries() {
    this.orderService.getInstruments().subscribe(instruments => {
      for (const instrument of instruments) {
        const orderSerie = {  
          name: instrument.name,
          series: []
        } as OrdersSeries;
  
        this.executedOrders.push(orderSerie);
      }
    })

  }

  private setExecutedOrderSeries(ordersSeries:OrderSeriesDto) {
    for (const executedOrder of this.executedOrders) {
      
      if (executedOrder.name === ordersSeries.instrumentName) {

        const orderSerieXY = {  
          name: new Date(ordersSeries.timestamp),
          value: ordersSeries.counter
        } as OrdersSeriesXY;

        executedOrder.series.push(orderSerieXY)
      }
    }
    this.executedOrders = [...this.executedOrders];
  }
  
  yAxisFormat(val : any) {
    if (val % 1 > 0) return "";
    return val ;
 }

 xAxisFormat(val: any) {
    return (<Date>val).toLocaleTimeString();
  }
}
