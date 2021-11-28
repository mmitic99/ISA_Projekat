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
      this.router.navigate([''])
    }
    this.loginUser = new LoginUser("", "")
  }

  login(): void {
    this.authService.login(this.loginUser).subscribe(
      (data) => {
        this.loginData = data;
        localStorage.setItem('accessToken', this.loginData.accessToken)
        localStorage.setItem('role', this.loginData.role)
        this.toastr.success('Uspešno ste se prijavili na naš sistem. Sada možete nastaviti da koristite aplikaciju.')
        
        // TODO: izmeni navigaciju u zavisnosti od tipa korisnika
        this.router.navigate([''])
      },
      (error) => {
        this.toastr.error(error.error)
      }
    )
  }

}
