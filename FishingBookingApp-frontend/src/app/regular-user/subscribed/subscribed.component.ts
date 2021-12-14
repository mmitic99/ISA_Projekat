import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-subscribed',
  templateUrl: './subscribed.component.html',
  styleUrls: ['./subscribed.component.css']
})
export class SubscribedComponent implements OnInit {

  subscribedReservationEntities : any;

  constructor(private userService: UserService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.getAllSubscription()
  }

  getAllSubscription() {
    this.userService.getSubsription().subscribe((data)=>{
      this.subscribedReservationEntities = data;
    },(error)=>{
      this.toastr.error(error)
      this.subscribedReservationEntities = []
    })
  }

}
