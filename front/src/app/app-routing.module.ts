import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router'
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register/register.component';

const routes: Routes = [
  {path:"", redirectTo: "login", pathMatch: "full"},
  {path:"login", component:LoginComponent},
  {path:"register", component:RegisterComponent},
  {path:"home", component:HomeComponent}
]

// configures NgModule imports and exports
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
