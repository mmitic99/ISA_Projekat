import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ComplaintComponent } from './complaint/complaint.component';
import { CurentReservationComponent } from './curent-reservation/curent-reservation.component';
import { PenaltiesComponent } from './penalties/penalties.component';
import { ProfileComponent } from './profile/profile.component';
import { RequestForDeletingAccountComponent } from './request-for-deleting-account/request-for-deleting-account.component';
import { ReservationComponent } from './reservation/reservation.component';
import { SubscribedComponent } from './subscribed/subscribed.component';

const routes: Routes = [
  { path: 'user_profile', component: ProfileComponent },
  { path: 'reservation', component: ReservationComponent },
  { path: 'curent_reservation', component: CurentReservationComponent },
  { path: 'deleting_account', component: RequestForDeletingAccountComponent },
  { path: 'complaint', component: ComplaintComponent },
  { path: 'subscribed', component: SubscribedComponent },
  { path: 'penalties', component: PenaltiesComponent },
  { path: 'change_password', component: ChangePasswordComponent },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes),
  ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class RegularUserRoutingModule { }
