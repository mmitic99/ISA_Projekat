import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ReservationEntityComponent } from './reservation-entity/reservation-entity.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'reservationEntity/:id', component: ReservationEntityComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UnauthorizedUserRoutingModule { }
