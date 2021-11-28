import { Component, OnInit } from '@angular/core';
import { Renderer2 } from '@angular/core';
import { AuthService } from '../unauthorized-user/service/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  }

}