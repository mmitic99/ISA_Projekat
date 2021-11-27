import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UnauthorizedUserRoutingModule } from './unauthorized-user-routing.module';
import { HomeComponent } from './home/home.component';
import { ReservationEntityComponent } from './reservation-entity/reservation-entity.component';


@NgModule({
  declarations: [
    HomeComponent,
    ReservationEntityComponent
  ],
  imports: [
    CommonModule,
    UnauthorizedUserRoutingModule
  ]
})
export class UnauthorizedUserModule { }
