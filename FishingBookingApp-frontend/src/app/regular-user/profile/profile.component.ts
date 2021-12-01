import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { RegistrationUser } from 'src/app/unauthorized-user/registration/registrationUser';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  editUser: any;


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
    this.userService.editProfile(this.editUser).subscribe(
      (data)=>{
        this.toastr.success('Uspešno ste izmenili profil.')
      }
    )
  }

}
