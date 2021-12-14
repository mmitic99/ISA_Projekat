import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SubscriptionService } from 'src/app/regular-user/service/subscription.service';
import { Subscription } from 'src/app/regular-user/subscribed/Subscription';
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
  subscriptions: any;

  constructor(
    private reservationEntitiesService: ReservationEntitiesService,
    public authService: AuthService,
    private subscriptionService: SubscriptionService,
    private toastr: ToastrService
    ) { }

  ngOnInit(): void {
    this.getAllReservationEntities();
    this.getAllSubscriptions();
  }
  getAllSubscriptions() {
    this.subscriptionService.getSubsription().subscribe((data)=>{
      this.subscriptions = data;
    });
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
      this.reservationEntitiesService.getOneEntityImage(entity.id).subscribe(
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

  subscribe(id: any){
    let subscription = new  Subscription("", id, true)
    this.subscriptionService.reservationEntitySubscription(subscription).subscribe((data)=>{
      this.toastr.success("Usepšno ste se pretplatili")
      this.getAllSubscriptions()
    },(error)=>{
      this.toastr.error(error)
    });
  }

  isSubscribed(reservationEntityId: any){
    var retVal = false;
    var mailAddress = localStorage.getItem('mailAddress');
    
    /*
    for(let subscription of this.subscriptions){
      if(subscription.user.mailAddress == mailAddress && subscription.reservationEntities.id == reservationEntityId){
        retVal = true;
        break;
      }
    }*/

    var filtered = this.subscriptions.filter((subscription: { user: { mailAddress: string | null; }; reservationEntities: { id: any; }; })=>(subscription.user.mailAddress == mailAddress && subscription.reservationEntities.id == reservationEntityId))

    if(filtered.length != 0){
      retVal = true
    }
    else{
      retVal = false;
    }

    return retVal;
  }
}
