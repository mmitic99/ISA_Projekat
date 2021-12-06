import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  editUser: any;
  isMailValid = true;
  isPasswordMatches = true;
  isPhoneNumberValid = true;
  isPostalCodeValid = true;

  constructor(private userService: UserService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.getProfile();
  }

  getProfile() {
    this.userService.getProfile().subscribe(
      (data)=>{
        this.editUser = data;
      }
    )
  }

  edit(){
    if (this.isAllValid()) {
      this.userService.editProfile(this.editUser).subscribe(
        (data)=>{
          this.toastr.success('Uspešno ste izmenili profil.')
        }
      )
    }
    else {
      this.toastr.error("Nisu popunjena sva polja ili su neka nevalidno unesena.")
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
    if (this.editUser.mailAddress == "" 
      //|| this.editUser.password1 == "" || this.editUser.password2 == "" 
      || this.editUser.name == ""
      || this.editUser.surname == "" || this.editUser.mobileNumber == ""
      || this.editUser.street == "" || this.editUser.number == ""
      || this.editUser.city == "" || this.editUser.postalCode == ""
      || this.editUser.country == "") {
      return false;
    }

    return true;
  }
  /*
  onFocusOutMail(): void {
    var regexp = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
    this.isMailValid = regexp.test(this.editUser.mailAddress);
  }

  onFocusOutPassword(): void {
    if (this.editUser.password1 != this.editUser.password2) {
      this.isPasswordMatches = false;
    }
    else {
      this.isPasswordMatches = true;
    }
  }
  */
  onFocusOutPhoneNumber(): void {
    var regexp = new RegExp('^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$');
    this.isPhoneNumberValid = regexp.test(this.editUser.mobileNumber);
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.editUser.postalCode);
  }

}
