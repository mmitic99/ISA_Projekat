import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReservationEntity } from '../new-entity/ReservationEntity';
import { EntitiesService } from '../service/entities.service';

@Component({
  selector: 'app-entity-reservation',
  templateUrl: './entity-reservation.component.html',
  styleUrls: ['./entity-reservation.component.css']
})
export class EntityReservationComponent implements OnInit {

  reservationEntity = new ReservationEntity("", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
  reservationsOfEntity: any;
  reservationsOfEntityToShow: any;
  reservationsType = 'all';
  private id: any;
  constructor(private route: ActivatedRoute, private entitiesService: EntitiesService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();
    this.getEntityReservations();
  }

  getEntity() {
    this.entitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.reservationEntity = new ReservationEntity(data.id, data.name, data.numberOfRooms, data.bedsPerRoom, data.price,
          data.promotionalDescription, data.rulesOfConduct, data.address.street,
          data.address.number, data.address.city, data.address.postalCode,
          data.address.country, this.reservationEntity.userId, this.reservationEntity.username, data.address.address_id);
      }
    )
  }

  getEntityReservations() {
    this.entitiesService.getAllReservationsOfEntity(this.id).subscribe(
      (data) => {
        this.reservationsOfEntity = data;
        this.reservationsOfEntityToShow = [];
        for (let reservation of this.reservationsOfEntity) {
          let dateTime = reservation.start
          reservation.start = new Date(dateTime[0], dateTime[1] - 1, dateTime[2], dateTime[3], dateTime[4])
          this.reservationsOfEntityToShow.push(reservation)
        }
      }
    )
  }

  reservationsToShow() {
    this.reservationsOfEntityToShow = []
    if (this.reservationsType == 'all') {
      for (let reservation of this.reservationsOfEntity) {
        this.reservationsOfEntityToShow.push(reservation)
      }
    } else if (this.reservationsType == 'future') {
      for (let reservation of this.reservationsOfEntity) {
        if (reservation.start > Date.now()) {
          this.reservationsOfEntityToShow.push(reservation)
        }
      }
    } else if (this.reservationsType == 'past') {
      for (let reservation of this.reservationsOfEntity) {
        let reservationEndTime = new Date(new Date(reservation.start).setHours(reservation.start.getHours() + reservation.durationInHours));
        if (reservationEndTime.getTime() < Date.now()) {
          this.reservationsOfEntityToShow.push(reservation)
        }
      }
    } else {
      for (let reservation of this.reservationsOfEntity) {
        if (reservation.start < Date.now()) {
          this.reservationsOfEntityToShow.push(reservation)
        }
      }
    }
  }

}
