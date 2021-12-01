import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RegularUserRoutingModule } from './regular-user-routing.module';
import { ProfileComponent } from './profile/profile.component';
import { HomePageComponent } from './home-page/home-page.component';


@NgModule({
  declarations: [
    ProfileComponent,
    HomePageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RegularUserRoutingModule
  ]
})
export class RegularUserModule { }
