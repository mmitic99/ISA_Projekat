import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { LoginUser } from './loginUser';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginData: any;

  constructor(private authService: AuthService, private toastr: ToastrService) { }

  loginUser = new LoginUser("", "")

  ngOnInit(): void {
    this.loginUser = new LoginUser("", "")
  }

  login(): void{
    this.authService.login(this.loginUser).subscribe(
      (data) =>{
          this.loginData = data;
          console.log(data)
          localStorage.setItem('accessToken', this.loginData.accessToken)
          localStorage.setItem('role', this.loginData.role)
      },
      (error) => {
        this.toastr.error(error.error)
        console.log(error)
      }
    )
  }

}
