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
  isMailValid = true;
  isPasswordMatches = true;
  isPhoneNumberValid = true;
  isPostalCodeValid = true;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
    if (localStorage.getItem('role') != null) {
        this.redirectUser();
    }
  }

  register(): void {
    if (this.isAllFilled()) {
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
    else {
      this.toastr.error("Nisu popunjena sva polja.")
    }
  }

  isAllValid(): boolean {
    if (this.isAllFilled() && this.isMailValid && this.isPasswordMatches && this.isPhoneNumberValid && this.isPostalCodeValid) {
      return true;
    }
    else {
      return false;
    }
  }

  isAllFilled(): boolean {
    if (this.registrationUser.mailAddress == "" || this.registrationUser.password1 == ""
      || this.registrationUser.password2 == "" || this.registrationUser.name == ""
      || this.registrationUser.surname == "" || this.registrationUser.mobileNumber == ""
      || this.registrationUser.street == "" || this.registrationUser.number == ""
      || this.registrationUser.city == "" || this.registrationUser.postalCode == ""
      || this.registrationUser.country == "") {
      return false;
    }

    return true;
  }

  onFocusOutMail(): void {
    var regexp = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
    this.isMailValid = regexp.test(this.registrationUser.mailAddress);
  }

  onFocusOutPassword(): void {
    if (this.registrationUser.password1 != this.registrationUser.password2) {
      this.isPasswordMatches = false;
    }
    else {
      this.isPasswordMatches = true;
    }
  }

  onFocusOutPhoneNumber(): void {
    var regexp = new RegExp('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$');
    this.isPhoneNumberValid = regexp.test(this.registrationUser.mobileNumber);
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.registrationUser.postalCode);
  }
  private redirectUser() {
    // TODO: dopuniti navigaciju
    if (localStorage.getItem('role') == "ROLE_USER") {
      this.router.navigate(['home']);
    }
    else {
      this.router.navigate(['']);
    }
  }
}
