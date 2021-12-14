import { Component, OnInit } from '@angular/core';
import { EntitiesService } from 'src/app/special-user/service/entities.service';
import { SuHomeComponent } from 'src/app/special-user/su-home/su-home.component';
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
  searchFilterSortModel= new SearchFilterSortModel("", new Array<string>());

  constructor(
    private reservationEntitiesService: ReservationEntitiesService,
    public authService: AuthService,
    private entitiesService : EntitiesService
    ) { }

  ngOnInit(): void {
    this.getAllReservationEntities();
  }

  getAllReservationEntities() {
    this.reservationEntitiesService.getAllReservationEntities().subscribe(
      (data)=>{
        this.reservationEntities = data
        this.getOneImageForEveryEntity(data);
      }
    )
  }

  searchFilterSort(){
    if(this.searchFilterSortModel.sort == "" && this.searchFilterSortModel.types.length == 0 && this.searchFilterSortModel.search == ""){
      this.getAllReservationEntities();
    }
    else{
      this.reservationEntitiesService.searchFilterSort(this.searchFilterSortModel).subscribe(
        (data)=>{
          this.reservationEntities = data;
          this.getOneImageForEveryEntity(data);
        },
        (error)=>{
          this.reservationEntities = []
        }
      )
    }
  }

  selectType(type: string){
    if (this.searchFilterSortModel.types.includes(type)) {
      this.searchFilterSortModel.types = this.searchFilterSortModel.types.filter(item => item !== type)
    } else {
      this.searchFilterSortModel.types.push(type)
    }
    this.searchFilterSort()
  }
  getOneImageForEveryEntity(entities : any) {
    this.reservationEntities = [];
    for (let entity of entities) {
      this.entitiesService.getOneEntityImage(entity.id).subscribe(
        (data) => {
          if (data != null) {
            entity.base64Image = 'data:image/jpg;base64,' + data.base64Image;
          }
          this.reservationEntities.push(entity);
          },
        () => {
          this.reservationEntities.push(entity);
          }
      )
    }
  }
}
