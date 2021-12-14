import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SubscriptionService } from '../service/subscription.service';
import { UserService } from '../service/user.service';
import { Subscription } from './Subscription';

@Component({
  selector: 'app-subscribed',
  templateUrl: './subscribed.component.html',
  styleUrls: ['./subscribed.component.css']
})
export class SubscribedComponent implements OnInit {

  subscribedReservationEntities : any;

  constructor(private userService: UserService, private subscriptionService: SubscriptionService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.getAllSubscription()
  }

  getAllSubscription() {
    this.subscriptionService.getSubsription().subscribe((data)=>{
      this.subscribedReservationEntities = data;
    },(error)=>{
      this.toastr.error(error)
      this.subscribedReservationEntities = []
    })
  }

  unsubscribe(id: any){
    var subscription = new Subscription("", id, false);
    this.subscriptionService.unsubscribeReservationEntity(subscription).subscribe((data)=>{
      this.toastr.success("Uspešno se otkazali pretragu")
      this.getAllSubscription()//TODO: add filter
    },(error)=>{
      this.toastr.error("Niste otkazali pretragu")
    });
  }

}
