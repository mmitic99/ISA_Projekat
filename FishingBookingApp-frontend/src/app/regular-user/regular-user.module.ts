import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RegularUserRoutingModule } from './regular-user-routing.module';
import { ProfileComponent } from './profile/profile.component';
import { ReservationComponent } from './reservation/reservation.component';
import { CurentReservationComponent } from './curent-reservation/curent-reservation.component';
import { RequestForDeletingAccountComponent } from './request-for-deleting-account/request-for-deleting-account.component';
import { ComplaintComponent } from './complaint/complaint.component';
import { SubscribedComponent } from './subscribed/subscribed.component';
import { PenaltiesComponent } from './penalties/penalties.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { AdditionalServicesReservationComponent } from './additional-services-reservation/additional-services-reservation.component';


@NgModule({
  declarations: [
    ProfileComponent,
    ReservationComponent,
    CurentReservationComponent,
    RequestForDeletingAccountComponent,
    ComplaintComponent,
    SubscribedComponent,
    PenaltiesComponent,
    ChangePasswordComponent,
    AdditionalServicesReservationComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RegularUserRoutingModule
  ]
})
export class RegularUserModule { }
