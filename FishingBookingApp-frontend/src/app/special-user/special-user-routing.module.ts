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
import { ProfileComponent } from './profile/profile.component';
import { SuHomeComponent } from './su-home/su-home.component';

const routes: Routes = [
    { path: '', component: SuHomeComponent},
    { path: 'userProfile', component: ProfileComponent },
    { path: 'createEntity', component: NewEntityComponent},
    { path: 'createBoat', component: NewBoatComponent},
    { path: 'viewEntity/:id', component: EntityViewComponent},
    { path: 'viewBoat/:id', component: BoatViewComponent},
    { path: 'entityReservations/:id', component: EntityReservationComponent},
    { path: 'additionalServices/:id', component: AdditionalServicesComponent},
    { path: 'entityBusyness', component: EntityBusynessComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes),
  ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class SpecialUserRoutingModule { }
