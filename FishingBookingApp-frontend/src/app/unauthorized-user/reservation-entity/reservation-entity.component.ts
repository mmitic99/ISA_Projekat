import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EntitiesService } from 'src/app/special-user/service/entities.service';
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

  constructor(private route: ActivatedRoute, private reservationEntitiesService: ReservationEntitiesService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();
    this.getEntityImages();
  }

  getEntity() {
    this.reservationEntitiesService.getEntity(this.id).subscribe(
      (data)=>{
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

}
