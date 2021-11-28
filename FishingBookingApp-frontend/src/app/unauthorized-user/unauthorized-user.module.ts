import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UnauthorizedUserRoutingModule } from './unauthorized-user-routing.module';
import { HomeComponent } from './home/home.component';
import { ReservationEntityComponent } from './reservation-entity/reservation-entity.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';


@NgModule({
  declarations: [
    HomeComponent,
    ReservationEntityComponent,
    LoginComponent,
    RegistrationComponent
  ],
  imports: [
    CommonModule,
    UnauthorizedUserRoutingModule
  ]
})
export class UnauthorizedUserModule { }
