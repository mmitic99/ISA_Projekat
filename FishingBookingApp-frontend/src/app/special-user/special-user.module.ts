import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { SpecialUserRoutingModule } from './special-user-routing.module';
import { ProfileComponent } from './profile/profile.component';
import { SuHomeComponent } from './su-home/su-home.component';


@NgModule({
  declarations: [
    ProfileComponent,
    SuHomeComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SpecialUserRoutingModule
  ]
})
export class SpecialUserModule { }
