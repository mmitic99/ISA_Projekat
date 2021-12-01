import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { serverPortApi, serverPortAuth } from 'src/app/app.consts';
import { LoginUser } from '../login/loginUser';
import { RegistrationUser } from '../registration/registrationUser';
import { SpecialRegistrationUser } from '../special-registration/specialRegistrationUser';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router) { }

  login(loginUser: LoginUser) {
    return this.http.post(serverPortAuth + 'login', loginUser);
  }
  logout(){
    localStorage.removeItem('role')
    localStorage.removeItem('accessToken')
    this.router.navigate([''])
    
  }

  register(registrationUser: RegistrationUser) {
    return this.http.post(serverPortAuth + 'signup', registrationUser);
  }

  specialRegister(registrationUser: SpecialRegistrationUser) {
    return this.http.post(serverPortAuth + 'signup', registrationUser);
  }

  getRole(){
    return localStorage.getItem('role');
  }
}
