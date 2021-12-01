import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { CurentReservationComponent } from './curent-reservation/curent-reservation.component';
import { ProfileComponent } from './profile/profile.component';
import { ReservationComponent } from './reservation/reservation.component';

const routes: Routes = [
  { path: 'user_profile', component: ProfileComponent },
  { path: 'reservation', component: ReservationComponent },
  { path: 'curent_reservation', component: CurentReservationComponent },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes),
  ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class RegularUserRoutingModule { }
