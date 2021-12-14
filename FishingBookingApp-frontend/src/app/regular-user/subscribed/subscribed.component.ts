import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';
import { SubscriptionService } from '../service/subscription.service';
import { UserService } from '../service/user.service';
import { Subscription } from './Subscription';

@Component({
  selector: 'app-subscribed',
  templateUrl: './subscribed.component.html',
  styleUrls: ['./subscribed.component.css']
})
export class SubscribedComponent implements OnInit {

  subscribedReservationEntities : any;
  searchFilterSortModel= new SearchFilterSortModel("", new Array<string>());

  constructor(private userService: UserService, private subscriptionService: SubscriptionService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.getAllSubscription()
  }

  getAllSubscription() {
    this.subscriptionService.getSubsriptions().subscribe((data)=>{
      this.subscribedReservationEntities = data;
    },(error)=>{
      this.toastr.error(error)
      this.subscribedReservationEntities = []
    })
  }

  unsubscribe(id: any){
    var subscription = new Subscription("", id, false);
    this.subscriptionService.reservationEntitySubscription(subscription).subscribe((data)=>{
      this.toastr.success("Uspešno se otkazali pretragu")
      this.getAllSubscription()//TODO: add filter
    },(error)=>{
      this.toastr.error("Niste otkazali pretragu")
    });
  }

  searchFilterSort(){
    if(this.searchFilterSortModel.types.length == 0){
      this.getAllSubscription();
    }
    else{
      this.subscriptionService.searchFilterSort(this.searchFilterSortModel).subscribe(
        (data)=>{
          this.subscribedReservationEntities = data;
        },
        (error)=>{
          this.subscribedReservationEntities = []
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

}
