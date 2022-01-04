import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SpecialReservationService } from 'src/app/regular-user/service/special-reservation.service';
import { SubscriptionService } from 'src/app/regular-user/service/subscription.service';
import { Subscription } from 'src/app/regular-user/subscribed/Subscription';
import { EntitiesService } from 'src/app/special-user/service/entities.service';
import { AuthService } from '../service/auth.service';
import { ReservationEntitiesService } from '../service/reservation-entities.service';

import { View, Feature, Map, Tile } from 'ol';
import VectorLayer from 'ol/layer/Vector';
import { fromLonLat, get as GetProjection, toLonLat } from 'ol/proj'
import TileLayer from 'ol/layer/Tile';
import OSM, { ATTRIBUTION } from 'ol/source/OSM';
import Point from 'ol/geom/Point';
import VectorSource from 'ol/source/Vector';

@Component({
  selector: 'app-reservation-entity',
  templateUrl: './reservation-entity.component.html',
  styleUrls: ['./reservation-entity.component.css']
})
export class ReservationEntityComponent implements OnInit {

  map: Map | undefined;
  private id: any;
  reservationEntity: any;
  imageObject: Array<object> = [];
  subscription: any;
  specialReservations: any;

  constructor(private route: ActivatedRoute,
    private reservationEntitiesService: ReservationEntitiesService,
    public authService: AuthService,
    private subscriptionService: SubscriptionService,
    private toastr: ToastrService,
    private specialReservationService: SpecialReservationService,
    private router: Router,
    private datePipe: DatePipe
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();
    this.getEntityImages();
    this.getSubscription();
    if (this.authService.getRole() == "ROLE_USER") {
      this.getAllSpecialReservation();
    }
  }

  getAllSpecialReservation() {
    this.specialReservationService.getAllSpecialReservation(this.id).subscribe(
      (data) => {
        this.specialReservations = data;
        for (let reservation of this.specialReservations) {
          let dateTime = reservation.start
          reservation.start = new Date(dateTime[0], dateTime[1] - 1, dateTime[2], dateTime[3], dateTime[4])
          dateTime = reservation.validFrom
          reservation.validFrom = new Date(dateTime[0], dateTime[1] - 1, dateTime[2], dateTime[3], dateTime[4])
          dateTime = reservation.validTo
          reservation.validTo = new Date(dateTime[0], dateTime[1] - 1, dateTime[2], dateTime[3], dateTime[4])
        }
      },
      (error) => {
        this.specialReservations = []
      }
    )
  }

  reserve(specialReservation: any) {
    var url = '/aditional_services_reservation/' + this.id +
      '?date=' + this.datePipe.transform(specialReservation.start, "yyyy-MM-dd") +
      '&time=' + this.datePipe.transform(specialReservation.start, "HH:mm") +
      '&daysNumber=' + specialReservation.durationInHours / 24 +
      '&guestsNumber=' + specialReservation.maxPeople +
      '&price=' + specialReservation.price +
      '&specResId=' + specialReservation.id +
      '&isSpecial=true';

    for (var addSer of specialReservation.additionalServices) {
      url += '&addSerIds=' + addSer.id
    }

    this.router.navigateByUrl(url);
  }

  getEntity() {
    this.reservationEntitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.reservationEntity = data
        this.initializeMap();
      },
      (error) => {
        if (error.status == 401) {
          AuthService.logout()
        }
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
      }, (error) => {
        if (error.status == 401) {
          AuthService.logout()
        }
      }
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
      else {
        this.toastr.success("Uspešno ste se otkazali pretplatu")
        this.subscription = null
      }
    }, (error) => {
      this.toastr.error(error)
      if (error.status == 401) {
        AuthService.logout()
      }
    });
  }

  initializeMap() {
    const features = [];
    features.push(new Feature({
      geometry: new Point(fromLonLat([this.reservationEntity.address.longitude, this.reservationEntity.address.latitude]))
    }))
    const vectorSource = new VectorSource({
      features
    });
    const vectorLayer = new VectorLayer({
      source: vectorSource
    });

    this.map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM({})
        }),
        vectorLayer
      ],
      view: new View({
        center: fromLonLat([this.reservationEntity.address.longitude, this.reservationEntity.address.latitude]),
        zoom: 15
      })
    });
  }

}
