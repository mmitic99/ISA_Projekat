import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { ReservationEntityComponent } from './reservation-entity/reservation-entity.component';
import { ToastrModule } from 'ngx-toastr';
import { SpecialRegistrationComponent } from './special-registration/special-registration.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'reservationEntity/:id', component: ReservationEntityComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'specialRegistration', component: SpecialRegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes),
  ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class UnauthorizedUserRoutingModule { }
