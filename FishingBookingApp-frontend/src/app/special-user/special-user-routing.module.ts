import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { EntityReservationComponent } from './entity-reservation/entity-reservation.component';
import { EntityViewComponent } from './entity-view/entity-view.component';
import { NewEntityComponent } from './new-entity/new-entity.component';
import { ProfileComponent } from './profile/profile.component';
import { SuHomeComponent } from './su-home/su-home.component';

const routes: Routes = [
    { path: '', component: SuHomeComponent},
    { path: 'userProfile', component: ProfileComponent },
    { path: 'createEntity', component: NewEntityComponent},
    { path: 'viewEntity/:id', component: EntityViewComponent},
    { path: 'entityReservations/:id', component: EntityReservationComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes),
  ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class SpecialUserRoutingModule { }
