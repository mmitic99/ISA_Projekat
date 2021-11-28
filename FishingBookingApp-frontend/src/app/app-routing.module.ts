import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';

const routes: Routes = [
  { path: '', loadChildren: () => import('./unauthorized-user/unauthorized-user.module').then(mode => mode.UnauthorizedUserModule) },

];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    ToastrModule.forRoot(),],
  exports: [RouterModule]
})
export class AppRoutingModule { }
