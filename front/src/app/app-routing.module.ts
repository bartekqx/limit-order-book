import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router'
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register/register.component';
import { CreateOrderComponent } from './components/create-order/create-order.component';
import { ViewOrdersComponent } from './components/view-orders/view-orders/view-orders.component';
import { MyProfileComponent } from './components/my-profile/my-profile/my-profile.component';

const routes: Routes = [
  {path:"", redirectTo: "login", pathMatch: "full"},
  {path:"login", component:LoginComponent},
  {path:"register", component:RegisterComponent},
  {path:"home", component:HomeComponent},
  {path:"create-order", component:CreateOrderComponent},
  {path:"view-orders", component:ViewOrdersComponent},
  {path:"my-profile", component:MyProfileComponent}
]

// configures NgModule imports and exports
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
