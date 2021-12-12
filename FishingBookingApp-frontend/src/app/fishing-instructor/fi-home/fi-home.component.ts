import { Component, OnInit } from '@angular/core';
import { EntitiesService } from '../service/entities.service';

@Component({
  selector: 'app-fi-home',
  templateUrl: './fi-home.component.html',
  styleUrls: ['./fi-home.component.css']
})
export class FiHomeComponent implements OnInit {

  reservationEntities: any;
  reservationEntitiesToShow: any;
  searchParameter = "";
  constructor(private entitiesService : EntitiesService) { }

  ngOnInit(): void {
    this.getAppropriateReservationEntities();
  }

  getAppropriateReservationEntities() {
    var role = localStorage.getItem('role');
    if (role == 'ROLE_fishingInstructor') {
      this.entitiesService.getAllUserClasses().subscribe(
        (data) => {
          this.reservationEntities = data;
          this.reservationEntitiesToShow = data;
        }
      )
    }

  }

  search() {
    this.reservationEntitiesToShow = [];
    for (let entity of this.reservationEntities) {
      if (entity.name.toLowerCase().includes(this.searchParameter.toLowerCase())){
        this.reservationEntitiesToShow.push(entity);
      }
      /* || entity.address.street.toLowerCase().include(this.searchParameter.toLowerCase()) 
        || entity.address.city.toLowerCase().include(this.searchParameter.toLowerCase()) || entity.address.number.toLowerCase().include(this.searchParameter.toLowerCase()) 
        || entity.address.country.toLowerCase().include(this.searchParameter.toLowerCase()) || entity.address.postalCode.toLowerCase().include(this.searchParameter.toLowerCase()) 
        || entity.promotionalDescription.toLowerCase().include(this.searchParameter.toLowerCase()))
        */
    }
  }

}
