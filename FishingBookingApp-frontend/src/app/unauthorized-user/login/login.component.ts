import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { LoginUser } from './loginUser';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginData: any;

  constructor(private authService: AuthService, private toastr: ToastrService, private router: Router) { }

  loginUser = new LoginUser("", "")

  ngOnInit(): void {
    if (localStorage.getItem('role') != null) {
        this.redirectUser();
    }
    this.loginUser = new LoginUser("", "")
  }

  login(): void {
    this.authService.login(this.loginUser).subscribe(
      (data) => {
        this.loginData = data;
        localStorage.setItem('accessToken', this.loginData.accessToken)
        localStorage.setItem('role', this.loginData.role)
        localStorage.setItem('mailAddress', this.loginData.mailAddress)
        this.toastr.success('Uspešno ste se prijavili na naš sistem. Sada možete nastaviti da koristite aplikaciju.')

        this.redirectUser();
      },
      (error) => {
        this.toastr.error(error.error)
      }
    )
  }


  private redirectUser() {
    // TODO: dopuniti navigaciju
    if (localStorage.getItem('role') == "ROLE_cottageOwner"){
      this.router.navigate(['specialUser']);
    }
    else if (localStorage.getItem('role') == "TODO") {
    }
    else {
      this.router.navigate(['']);
    }
  }
}
