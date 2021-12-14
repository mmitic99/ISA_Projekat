import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SubscriptionService } from 'src/app/regular-user/service/subscription.service';
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
  isSubscribe: any;

  constructor(private route: ActivatedRoute,
    private reservationEntitiesService: ReservationEntitiesService,
    public authService: AuthService,
    private subscribeService: SubscriptionService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();
    this.getEntityImages();
    this.isSubscribe = this.isSubscribed();
  }

  getEntity() {
    this.reservationEntitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.reservationEntity = data
      }
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
      }
    )
  }

  isSubscribed() {
    this.subscribeService.checkSubscription(this.id).subscribe((data) => {
      console.log(data)
      if (data == null) {
        return true;
      }
      return false;
    }, (error) => {
      console.log(error)
      return false;
    })
    return false
  }

  subscribe() {

  }

}
