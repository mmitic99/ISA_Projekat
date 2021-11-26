import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UnauthorizedUserRoutingModule } from './unauthorized-user-routing.module';
import { HomeComponent } from './home/home.component';


@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule,
    UnauthorizedUserRoutingModule
  ]
})
export class UnauthorizedUserModule { }
