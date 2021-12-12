import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';

const routes: Routes = [
  { path: '', loadChildren: () => import('./unauthorized-user/unauthorized-user.module').then(mode => mode.UnauthorizedUserModule) },
  { path: '', loadChildren: () => import('./regular-user/regular-user.module').then(mode => mode.RegularUserModule) },
  { path: 'specialUser', loadChildren: () => import('./special-user/special-user.module').then(mode => mode.SpecialUserModule) },
  { path: 'fishingInstructor', loadChildren: () => import('./fishing-instructor/fishing-instructor.module').then(mode => mode.FishingInstructorModule) },

];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class AppRoutingModule { }
