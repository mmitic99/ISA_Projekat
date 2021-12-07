import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/unauthorized-user/service/auth.service';
import { ChangePassword } from './ChangePassword';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  changePasswordModel = new ChangePassword("")

  constructor(private authService: AuthService, private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  changePassword() {
    if (this.changePasswordModel.oldPassword != "" && this.changePasswordModel.newPassword1 != "") {
      if (this.changePasswordModel.newPassword1 == this.changePasswordModel.newPassword2) {
        this.authService.changePassword(this.changePasswordModel).subscribe(
          (data) => {
            this.toastr.success('Uspešno ste izmenili svoju lozinku')
          },
          (error) => {
            this.toastr.error(error.error)
          })
      }
      else {

        this.toastr.error("Lozinke se ne podudaraju")
      }
    } else {
      this.toastr.error("Polja ne smeju biti prazna")
    }
  }
}
