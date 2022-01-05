import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Renderer2 } from '@angular/core';
import { AuthService } from '../unauthorized-user/service/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  
  @ViewChild('navBurger')
  navBurger!: ElementRef;
  @ViewChild('navMenu')
  navMenu!: ElementRef;

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  }

  toggleNavbar() {
    this.navBurger.nativeElement.classList.toggle('is-active');
    this.navMenu.nativeElement.classList.toggle('is-active');
  }


}