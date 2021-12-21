import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SubscriptionService } from 'src/app/regular-user/service/subscription.service';
import { Subscription } from 'src/app/regular-user/subscribed/Subscription';
import { EntitiesService } from 'src/app/special-user/service/entities.service';
import { AuthService } from '../service/auth.service';
import { ReservationEntitiesService } from '../service/reservation-entities.service';

@Component({
  selector: 'app-reservation-entity',
  templateUrl: './reservation-entity.component.html',
  styleUrls: ['./reservation-entity.component.css']
})
export class ReservationEntityComponent implements OnInit {

  private id: any;
  reservationEntity: any;
  imageObject: Array<object> = [];
  subscription: any;

  constructor(private route: ActivatedRoute,
    private reservationEntitiesService: ReservationEntitiesService,
    public authService: AuthService,
    private subscriptionService: SubscriptionService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();
    this.getEntityImages();
    this.getSubscription();
  }

  getEntity() {
    this.reservationEntitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.reservationEntity = data
      },
      (error)=>{
        if(error.status == 401){
          AuthService.logout()
        }}
    )
  }

  getEntityImages() {
    this.reservationEntitiesService.getEntityImages(this.id).subscribe(
      (data) => {
        if (data.length > 0) {
          for (let base64Image of data) {
            let img = 'data:image/jpg;base64,' + base64Image;
            this.imageObject.push({ image: img, thumbImage: img });
          }
        }
      },(error)=>{
        if(error.status == 401){
          AuthService.logout()
        }}
    )
  }

  getSubscription() {
    this.subscriptionService.checkSubscriptionForReservationEntityId(this.id).subscribe(
      (data) => {
        this.subscription = data
      }
    )
  }

  isSubscribed() {
    if (this.subscription != null) {
      return true;
    }
    else {
      return false;
    }
  }

  subscribe(subscribe: boolean) {
    let subscription = new Subscription("", this.id, subscribe)
    this.subscriptionService.reservationEntitySubscription(subscription).subscribe((data) => {
      if (subscribe) {
        this.toastr.success("Uspešno ste se pretplatili")
        this.subscription = data
      }
      else{
        this.toastr.success("Uspešno ste se otkazali pretplatu")
        this.subscription = null
      }
    }, (error) => {
      this.toastr.error(error)
      if(error.status == 401){
        AuthService.logout()
      }
    });
  }

}
