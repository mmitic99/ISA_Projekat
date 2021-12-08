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
  private id: any;
  constructor(private route: ActivatedRoute, private entitiesService: EntitiesService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();;
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

}
