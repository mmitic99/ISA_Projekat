import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { RegistrationUser } from './registrationUser';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationUser = new RegistrationUser("", "", "", "", "", "", "", "", "", "", "");

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  register(): void {
    this.authService.register(this.registrationUser).subscribe(
      (data) => {
        this.toastr.success("Uspešno ste se registrovali. Na email koji ste uneli će vam stići link za verifikaciju. Nakon što verifikujete email, možete se prijaviti ovde.", "Uspešna registracija", { timeOut: 100000 })
        this.router.navigate(['/login'])
      },
      (error) => {
        this.toastr.error(error.error)
      }
    )
  }


}
