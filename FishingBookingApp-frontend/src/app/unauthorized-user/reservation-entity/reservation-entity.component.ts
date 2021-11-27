import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReservationEntitiesService } from '../service/reservation-entities.service';

@Component({
  selector: 'app-reservation-entity',
  templateUrl: './reservation-entity.component.html',
  styleUrls: ['./reservation-entity.component.css']
})
export class ReservationEntityComponent implements OnInit {

  private id: any;
  reservationEntity: any;

  constructor(private route: ActivatedRoute, private reservationEntitiesService: ReservationEntitiesService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();;
  }

  getEntity() {
    this.reservationEntitiesService.getEntity(this.id).subscribe(
      (data)=>{
        this.reservationEntity = data
      }
    )
  }

}
