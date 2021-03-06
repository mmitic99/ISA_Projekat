import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgImageSliderModule } from 'ng-image-slider';
import { FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
// import interactionPlugin from '@fullcalendar/interaction'; Kada bude trebalo pise se u terminalu "npm i @fullcalendar/interaction"


import { SpecialUserRoutingModule } from './special-user-routing.module';
import { ProfileComponent } from './profile/profile.component';
import { SuHomeComponent } from './su-home/su-home.component';
import { NewEntityComponent } from './new-entity/new-entity.component';
import { EntityViewComponent } from './entity-view/entity-view.component';
import { EntityReservationComponent } from './entity-reservation/entity-reservation.component';
import { AdditionalServicesComponent } from './additional-services/additional-services.component';
import { EntityBusynessComponent } from './entity-busyness/entity-busyness.component';
import { NewBoatComponent } from './new-boat/new-boat.component';
import { BoatViewComponent } from './boat-view/boat-view.component';
import { NewAvailableAppointmentComponent } from './new-available-appointment/new-free-appointment.component';
import { SpecialReservationComponent } from './special-reservation/special-reservation.component';
import { NewReservationForUserComponent } from './new-reservation-for-user/new-reservation-for-user.component';
import { BasicProfileViewComponent } from './basic-profile-view/basic-profile-view.component';
import { ReservationViewComponent } from './reservation-view/reservation-view.component';
import { BusinessReportComponent } from './business-report/business-report.component';

FullCalendarModule.registerPlugins([ // register FullCalendar plugins
  dayGridPlugin,
  //interactionPlugin
]);

@NgModule({
  declarations: [
    ProfileComponent,
    SuHomeComponent,
    NewEntityComponent,
    EntityViewComponent,
    EntityReservationComponent,
    AdditionalServicesComponent,
    EntityBusynessComponent,
    NewBoatComponent,
    BoatViewComponent,
    NewAvailableAppointmentComponent,
    SpecialReservationComponent,
    NewReservationForUserComponent,
    BasicProfileViewComponent,
    ReservationViewComponent,
    BusinessReportComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SpecialUserRoutingModule,
    NgImageSliderModule,
    FullCalendarModule
  ]
})
export class SpecialUserModule { }
