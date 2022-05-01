import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { OrderSeriesDto, OrderService} from 'src/app/services/order/order.service';
import { OrdersSeries} from 'src/app/services/order/order.service';
import { OrdersSeriesXY} from 'src/app/services/order/order.service';
import { TransactionService } from 'src/app/services/transaction/transaction-service';


@Component({
  selector: 'app-analysis',
  templateUrl: './analysis.component.html',
  styleUrls: ['./analysis.component.css']
})
export class AnalysisComponent implements OnInit {

  constructor(private router:Router, private orderService:OrderService) {
  }

  instrumentsNames: string[] = [] ;
  pendingOrders: OrdersSeries[] = [] ;
  view: [number, number] = [1000,700];

  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Time';
  pendingOrders_yAxisLabel: string = 'Pending Orders';
  timeline: boolean = true;
  colorScheme: Color = {
    name: 'myScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#704FC4', '#4B852C', '#B67A3D', '#5B6FC8', '#25706F', '#C44F5B', '#4FBAC4', '#B6E04C']
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


    this.orderService.getPendingOrders().subscribe((event) => {
      let ordersSeriesJSON = JSON.parse(event.data); 
      let pendingOrders = <OrderSeriesDto>ordersSeriesJSON;

      this.setPendingOrderSeries(pendingOrders);
    })
  }

  private initSeries() {
    this.orderService.getInstruments().subscribe(instruments => {
      for (const instrument of instruments) {
        const orderSerie = {  
          name: instrument.name,
          series: []
        } as OrdersSeries;
  
        this.pendingOrders.push(orderSerie);
      }
    })

  }

  private setPendingOrderSeries(ordersSeries:OrderSeriesDto) {
    for (const pendingOrder of this.pendingOrders) {
      
      if (pendingOrder.name === ordersSeries.instrumentName) {
      
        const orderSerieXY = {  
          name: new Date(ordersSeries.timestamp),
          value: ordersSeries.counter
        } as OrdersSeriesXY;

        pendingOrder.series.push(orderSerieXY)
      }
    }
    this.pendingOrders = [...this.pendingOrders];
  }

  
  yAxisFormat(val : any) {
    if (val % 1 > 0) return "";
    return val ;
 }

 xAxisFormat(val: any) {
    return (<Date>val).toLocaleTimeString();
  }
}
