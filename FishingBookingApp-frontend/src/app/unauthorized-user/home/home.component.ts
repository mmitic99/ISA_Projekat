import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { ReservationEntitiesService } from '../service/reservation-entities.service';
import { SearchFilterSortModel } from './searchFilterSortModel';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  reservationEntities: any;
  searchFilterSortModel= new SearchFilterSortModel("");

  constructor(
    private reservationEntitiesService: ReservationEntitiesService,
    public authService: AuthService
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

  searchFilterSort(){
    if(this.searchFilterSortModel.sort == ""){
      this.getAllReservationEntities();
    }
    else{
      this.reservationEntitiesService.searchFilterSort(this.searchFilterSortModel).subscribe(
        (data)=>{
          this.reservationEntities = data;
        },
        (error)=>{
          this.reservationEntities = []
        }
      )
    }
  }

}
