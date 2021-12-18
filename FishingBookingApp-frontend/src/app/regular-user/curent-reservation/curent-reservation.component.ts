import { Component, OnInit } from '@angular/core';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';
import { ReservationService } from '../service/reservation.service';
import { CancelReservation } from './CancelReservation';

@Component({
  selector: 'app-curent-reservation',
  templateUrl: './curent-reservation.component.html',
  styleUrls: ['./curent-reservation.component.css']
})
export class CurentReservationComponent implements OnInit {

  searchFilterSortModel = new SearchFilterSortModel("")
  curentReservations: any;
  constructor(private reservationService: ReservationService,

  ) { }

  ngOnInit(): void {
    this.getCurrentReservation();
  }
  getCurrentReservation() {
    this.reservationService.getCurrentReservation().subscribe(
      (data) => { 
        this.curentReservations = data
        for(let curentReservation of this.curentReservations){
          let dateTime = curentReservation.start
          curentReservation.start = new Date(dateTime[0], dateTime[1]-1, dateTime[2], dateTime[3], dateTime[4])
          
        }
      },
      (error) => { this.curentReservations = [] })
  }

  isPossibleToCancel(start: Date){
    let nowDate = new Date()
    nowDate.setDate(nowDate.getDate() + 3)
    return true
    return nowDate <= start
  }

  cancelReservation(id: number){
    this.reservationService.cancelReservation(new CancelReservation(id,"")).subscribe(
      (data)=>{
        this.getCurrentReservation()
      },
      (error)=>{
        console.log(error)
      }
    );
  }

}
