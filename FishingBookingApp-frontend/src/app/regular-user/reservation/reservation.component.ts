import { Component, OnInit } from '@angular/core';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';
import { ReservationService } from '../service/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  searchFilterSortModel = new SearchFilterSortModel("")
  reservations: any;
  constructor(
    private reservationService: ReservationService
  ) { }

  ngOnInit(): void {
    this.getAllOldReservation();
  }

  getAllOldReservation() {
    this.reservationService.getAllOldReservation().subscribe(
      (data) => { 
        this.reservations = data
        for(let reservation of this.reservations){
          let dateTime = reservation.start
          reservation.start = new Date(dateTime[0], dateTime[1]-1, dateTime[2], dateTime[3], dateTime[4])
          
        }
      },
      (error) => { this.reservations = [] })
  }

}
