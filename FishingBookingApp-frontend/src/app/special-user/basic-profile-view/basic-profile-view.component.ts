import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/special-user/service/user.service';

@Component({
  selector: 'app-basic-profile-view',
  templateUrl: './basic-profile-view.component.html',
  styleUrls: ['./basic-profile-view.component.css']
})
export class BasicProfileViewComponent implements OnInit {

  userMailAddress: any;
  user: any;
  constructor(private _location: Location, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userMailAddress = params['mailAddress'];
    })
    this.getUser();
  }

  getUser() {
    this.userService.getUserByMailAddress(this.userMailAddress).subscribe(
      (data) => {
        this.user = data;
      }
    )
  }

  backClicked() {
    this._location.back();
  }

}
