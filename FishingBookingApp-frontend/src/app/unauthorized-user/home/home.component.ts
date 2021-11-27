import { Component, OnInit } from '@angular/core';
import { ReservationEntitiesService } from '../service/reservation-entities.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  reservationEntities: any;

  constructor(
    private reservationEntitiesService: ReservationEntitiesService,
    
    ) { }

  ngOnInit(): void {
    this.getAllReservationEntities();
  }

  getAllReservationEntities() {
    this.reservationEntitiesService.getAllReservationEntities().subscribe(
      (data)=>{
        this.reservationEntities = data
      }
    )
  }

}
