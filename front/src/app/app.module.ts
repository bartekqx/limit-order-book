import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { SignInService } from './services/sign-in/sign-in.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';


import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { NavBarComponent } from './components/nav-bar/nav-bar/nav-bar.component';
import { RegisterComponent } from './components/register/register/register.component';
import { TokenInterceptorService } from './services/interceptor/token-interceptor.service';
import { SignUpService } from './services/sign-up/sign-up.service';
import { OrderService } from './services/order/order.service';
import { CreateOrderComponent } from './components/create-order/create-order.component';
import { ViewOrdersComponent } from './components/view-orders/view-orders/view-orders.component';
import { MatTabsModule } from '@angular/material/tabs';
import { MyProfileComponent } from './components/my-profile/my-profile/my-profile.component';
import { TransactionService } from './services/transaction/transaction-service';
import { UserService } from './services/user/user.service';
import { LogoutService } from './services/logout/logout.service';
import { AnalysisComponent } from './components/analysis/analysis/analysis.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { ExecutedTransactionsAnalysisComponent } from './components/executed-transactions-analysis/executed-transactions-analysis/executed-transactions-analysis.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    NavBarComponent,
    RegisterComponent,
    CreateOrderComponent,
    ViewOrdersComponent,
    MyProfileComponent,
    AnalysisComponent,
    ExecutedTransactionsAnalysisComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSliderModule,
    AppRoutingModule,
    RouterModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatOptionModule,
    MatTabsModule,
    NgxChartsModule,
    BrowserAnimationsModule
  ],
  providers: [
    SignInService,
    SignUpService,
    OrderService,
    TransactionService,
    UserService,
    LogoutService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true },
  ],
  bootstrap: [AppComponent],
  exports: [AppRoutingModule]
})
export class AppModule { }
