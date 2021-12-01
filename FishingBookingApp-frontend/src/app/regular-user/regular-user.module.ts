import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RegularUserRoutingModule } from './regular-user-routing.module';
import { ProfileComponent } from './profile/profile.component';
import { ReservationComponent } from './reservation/reservation.component';
import { CurentReservationComponent } from './curent-reservation/curent-reservation.component';


@NgModule({
  declarations: [
    ProfileComponent,
    ReservationComponent,
    CurentReservationComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RegularUserRoutingModule
  ]
})
export class RegularUserModule { }
