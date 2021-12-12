import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { FishingInstructorRoutingModule } from './fishing-instructor-routing.module';
//import { ProfileComponent } from './profile/profile.component';
//import { SuHomeComponent } from './su-home/su-home.component';
//import { NewEntityComponent } from './new-entity/new-entity.component';
//import { EntityViewComponent } from './entity-view/entity-view.component';


@NgModule({
  declarations: [
    //ProfileComponent,
    //SuHomeComponent,
    //NewEntityComponent,
    //EntityViewComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    FishingInstructorRoutingModule
  ]
})
export class FishingInstructorModule { }
