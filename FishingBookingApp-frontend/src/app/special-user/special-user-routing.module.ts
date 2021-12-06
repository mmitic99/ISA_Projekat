import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
    { path: 'userProfile', component: ProfileComponent }
  
];

@NgModule({
  imports: [RouterModule.forChild(routes),
  ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class SpecialUserRoutingModule { }
