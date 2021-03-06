import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { AdditionalServicesComponent } from './additional-services/additional-services.component';
import { BoatViewComponent } from './boat-view/boat-view.component';
import { EntityBusynessComponent } from './entity-busyness/entity-busyness.component';
import { EntityReservationComponent } from './entity-reservation/entity-reservation.component';
import { EntityViewComponent } from './entity-view/entity-view.component';
import { NewBoatComponent } from './new-boat/new-boat.component';
import { NewEntityComponent } from './new-entity/new-entity.component';
import { NewAvailableAppointmentComponent } from './new-available-appointment/new-free-appointment.component';
import { ProfileComponent } from './profile/profile.component';
import { SuHomeComponent } from './su-home/su-home.component';
import { SpecialReservationComponent } from './special-reservation/special-reservation.component';
import { NewReservationForUserComponent } from './new-reservation-for-user/new-reservation-for-user.component';
import { BasicProfileViewComponent } from './basic-profile-view/basic-profile-view.component';
import { ReservationViewComponent } from './reservation-view/reservation-view.component';
import { BusinessReportComponent } from './business-report/business-report.component';

const routes: Routes = [
    { path: '', component: SuHomeComponent},
    { path: 'userProfile', component: ProfileComponent },
    { path: 'createEntity', component: NewEntityComponent},
    { path: 'createBoat', component: NewBoatComponent},
    { path: 'viewEntity/:id', component: EntityViewComponent},
    { path: 'viewBoat/:id', component: BoatViewComponent},
    { path: 'entityReservations/:id', component: EntityReservationComponent},
    { path: 'additionalServices/:id', component: AdditionalServicesComponent},
    { path: 'availableAppointments/:id', component: NewAvailableAppointmentComponent},
    { path: 'entityBusyness/:id', component: EntityBusynessComponent},
    { path: 'createSpecialReservation/:id', component: SpecialReservationComponent},
    { path: 'createReservationForUser/:id', component: NewReservationForUserComponent},
    { path: 'basicProfileView/:mailAddress', component: BasicProfileViewComponent},
    { path: 'reservationView/:id', component: ReservationViewComponent},
    { path: 'businessReport', component: BusinessReportComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes),
  ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class SpecialUserRoutingModule { }
