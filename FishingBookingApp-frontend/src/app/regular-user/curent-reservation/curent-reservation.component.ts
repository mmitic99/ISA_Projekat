import { Component, OnInit } from '@angular/core';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';
import { AuthService } from 'src/app/unauthorized-user/service/auth.service';
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
      (error) => { this.curentReservations = []
        if(error.status == 401){
          AuthService.logout()
        } })
  }

  isPossibleToCancel(start: Date){
    let nowDate = new Date()
    nowDate.setDate(nowDate.getDate() + 3)
    return nowDate <= start
  }

  cancelReservation(id: number){
    this.reservationService.cancelReservation(new CancelReservation(id,"")).subscribe(
      (data)=>{
        this.getCurrentReservation()
      },
      (error)=>{
        console.log(error)
        if(error.status == 401){
          AuthService.logout()
        }
      }
    );
  }
  searchFilterSort() {
    if (this.isParametersEmpty()) {
      this.getCurrentReservation();
    }
    else {
      this.onlySearchFilterSort();
    }
  }

  private onlySearchFilterSort() {
    this.searchFilterSortModel.isOldReservation = false;
    this.reservationService.searchFilterSort(this.searchFilterSortModel).subscribe(
      (data) => {
        this.curentReservations = data
        for(let curentReservation of this.curentReservations){
          let dateTime = curentReservation.start
          curentReservation.start = new Date(dateTime[0], dateTime[1]-1, dateTime[2], dateTime[3], dateTime[4])
          
        }
      },
      (error) => {
        this.curentReservations = [];
        if (error.status == 401) {
          AuthService.logout()
        }
      }
    );
  }

  private isParametersEmpty() {
    return this.searchFilterSortModel.sort == "" && this.searchFilterSortModel.types.length == 0;
  }
  
  selectType(type: string) {
    if (this.searchFilterSortModel.types.includes(type)) {
      this.searchFilterSortModel.types = this.searchFilterSortModel.types.filter(item => item !== type)
    } else {
      this.searchFilterSortModel.types.push(type)
    }
    this.searchFilterSort()
  }
}
