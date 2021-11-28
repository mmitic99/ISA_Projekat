import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginUser } from '../login/loginUser';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(loginUser: LoginUser) {
    return this.http.post("http://localhost:8080/auth/login", loginUser);
  }
}
