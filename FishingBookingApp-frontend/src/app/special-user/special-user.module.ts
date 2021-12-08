import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgImageSliderModule } from 'ng-image-slider';

import { SpecialUserRoutingModule } from './special-user-routing.module';
import { ProfileComponent } from './profile/profile.component';
import { SuHomeComponent } from './su-home/su-home.component';
import { NewEntityComponent } from './new-entity/new-entity.component';
import { EntityViewComponent } from './entity-view/entity-view.component';
import { EntityReservationComponent } from './entity-reservation/entity-reservation.component';


@NgModule({
  declarations: [
    ProfileComponent,
    SuHomeComponent,
    NewEntityComponent,
    EntityViewComponent,
    EntityReservationComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SpecialUserRoutingModule,
    NgImageSliderModule
  ]
})
export class SpecialUserModule { }
