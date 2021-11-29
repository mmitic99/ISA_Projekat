import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../service/auth.service';
import { SpecialRegistrationUser } from './specialRegistrationUser';

@Component({
  selector: 'app-special-registration',
  templateUrl: './special-registration.component.html',
  styleUrls: ['./special-registration.component.css']
})
export class SpecialRegistrationComponent implements OnInit {

  specialRegistrationUser = new SpecialRegistrationUser("", "", "", "", "", "", "", "", "", "", "", "", "");
  isMailValid = true;
  isPasswordMatches = true;
  isPhoneNumberValid = true;
  isPostalCodeValid = true;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  register(): void {
    if (this.isAllFilled()) {
      this.authService.specialRegister(this.specialRegistrationUser).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste se registrovali. Nakon što administrator odobri vašu registraciju dobićete obaveštenje putem mejla nakon čega se možete prijaviti.", "Uspešna registracija", { timeOut: 100000 })
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
    if (this.specialRegistrationUser.mailAddress == "" || this.specialRegistrationUser.password1 == ""
      || this.specialRegistrationUser.password2 == "" || this.specialRegistrationUser.name == ""
      || this.specialRegistrationUser.surname == "" || this.specialRegistrationUser.mobileNumber == ""
      || this.specialRegistrationUser.street == "" || this.specialRegistrationUser.number == ""
      || this.specialRegistrationUser.city == "" || this.specialRegistrationUser.postalCode == ""
      || this.specialRegistrationUser.country == "" || this.specialRegistrationUser.userRole == ""
      || this.specialRegistrationUser.explanationOfReg == "") {
      return false;
    }

    return true;
  }

  onFocusOutMail(): void {
    var regexp = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
    this.isMailValid = regexp.test(this.specialRegistrationUser.mailAddress);
  }

  onFocusOutPassword(): void {
    if (this.specialRegistrationUser.password1 != this.specialRegistrationUser.password2) {
      this.isPasswordMatches = false;
    }
    else {
      this.isPasswordMatches = true;
    }
  }

  onFocusOutPhoneNumber(): void {
    var regexp = new RegExp('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$');
    this.isPhoneNumberValid = regexp.test(this.specialRegistrationUser.mobileNumber);
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.specialRegistrationUser.postalCode);
  }
}
