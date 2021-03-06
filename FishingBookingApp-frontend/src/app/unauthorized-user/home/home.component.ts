import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationService } from 'src/app/regular-user/service/reservation.service';
import { SubscriptionService } from 'src/app/regular-user/service/subscription.service';
import { Subscription } from 'src/app/regular-user/subscribed/Subscription';
import { AuthService } from '../service/auth.service';
import { ReservationEntitiesService } from '../service/reservation-entities.service';
import { Reservation } from './reservation';
import { SearchFilterSortModel } from './searchFilterSortModel';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  reservationEntities: any;
  searchFilterSortModel = new SearchFilterSortModel("", "", "");
  subscriptions: any;
  times: string[] = [];
  reservationResponseReceived = false;
  minDate = new Date();
  minTime = 6
  maxTime = 24
  marks: any

  constructor(
    private reservationEntitiesService: ReservationEntitiesService,
    public authService: AuthService,
    private subscriptionService: SubscriptionService,
    private toastr: ToastrService,
    private router: Router,
    private reservationService: ReservationService,
  ) { }

  ngOnInit(): void {
    this.getAllReservationEntities();
    this.getAllSubscriptions();
    this.getTimes();
    this.getMarks()

    this.minDate = new Date();
  }
  getMarks() {
    this.reservationEntitiesService.getMarks().subscribe(
      (data) => {
        this.marks = data
      });
  }

  getTimes() {
    for (var i = this.minTime; i < this.maxTime; i++) {
      if (i < 10) {
        this.times.push('0' + i + ':' + '00')
        this.times.push('0' + i + ':' + '30')
      }
      else {
        this.times.push(i + ':' + '00')
        this.times.push(i + ':' + '30')
      }
    }
  }

  getAllSubscriptions() {
    this.subscriptionService.getSubsriptions().subscribe((data) => {
      this.subscriptions = data;
    });
  }

  getAllReservationEntities() {
    this.reservationEntitiesService.getAllReservationEntities().subscribe(
      (data) => {
        this.reservationEntities = data
        this.getOneImageForEveryEntity();
      },
      (error) => {

        if (error.status == 401) {
          AuthService.logout()
        }
      }
    )
  }

  searchFilterSort() {
    this.reservationResponseReceived = false;
    if (this.isParametersEmpty()) {
      this.getAllReservationEntities();
    }
    else if (this.isReservationParametersEmpty()) {
      this.onlySearchFilterSort();
    }
    else {
      this.checkReservation();
    }
  }

  private onlySearchFilterSort() {
    this.reservationEntitiesService.searchFilterSort(this.searchFilterSortModel).subscribe(
      (data) => {
        this.reservationEntities = data;
        this.getOneImageForEveryEntity();
      },
      (error) => {
        this.reservationEntities = [];
        if (error.status == 401) {
          AuthService.logout()
        }
      }
    );
  }

  private isReservationParametersEmpty() {
    return this.searchFilterSortModel.date == "" && this.searchFilterSortModel.time == "" &&
      this.searchFilterSortModel.daysNumber == 0 && this.searchFilterSortModel.guestsNumber == 0;
  }

  private isParametersEmpty() {
    return this.searchFilterSortModel.sort == "" && this.searchFilterSortModel.types.length == 0 &&
      this.searchFilterSortModel.search == "" && this.isReservationParametersEmpty();
  }

  private checkReservation() {
    if (this.reservationSearchIsNotEmpty()) {
      this.reservationEntitiesService.checkReservation(this.searchFilterSortModel).subscribe(
        (data) => {
          this.reservationEntities = data;
          this.getOneImageForEveryEntity();
          this.reservationResponseReceived = true;
        },
        (error) => {
          this.reservationEntities = [];
          if (error.status == 401) {
            AuthService.logout()
          }
        }
      );
    }
    else {
      this.toastr.error("Molimo vas da unesete sve kriterijume pretrage rezervacije");
    }
  }

  reservationSearchIsNotEmpty() {
    return this.searchFilterSortModel.date != "" && this.searchFilterSortModel.time != "" &&
      this.searchFilterSortModel.daysNumber != 0 && this.searchFilterSortModel.guestsNumber != 0;
  }

  checkIsReservationSearched() {
    return this.reservationSearchIsNotEmpty() && this.reservationResponseReceived;
  }

  selectType(type: string) {
    if (this.searchFilterSortModel.types.includes(type)) {
      this.searchFilterSortModel.types = this.searchFilterSortModel.types.filter(item => item !== type)
    } else {
      this.searchFilterSortModel.types.push(type)
    }
    this.searchFilterSort()
  }

  getOneImageForEveryEntity() {
    for (let entity of this.reservationEntities) {
      if (entity.type == "cottage") {
        entity.articleClass = "is-success"
      }
      else if (entity.type == "boat") {
        entity.articleClass = "is-info"
      }
      else if (entity.type == "fishingInstructor") {
        entity.articleClass = "is-warning"
      }
      else {
        entity.articleClass = ""
      }
      this.reservationEntitiesService.getOneEntityImage(entity.id).subscribe(
        (data) => {
          if (data != null) {
            entity.base64Image = 'data:image/jpg;base64,' + data.base64Image;
          }
        },
        (error) => {
          if (error.status == 401) {
            AuthService.logout()
          }
        }
      )
    }
  }

  subscribe(id: any) {
    let subscription = new Subscription("", id, true)
    this.subscriptionService.reservationEntitySubscription(subscription).subscribe((data) => {
      this.toastr.success("Uspe??no ste se pretplatili")
      this.getAllSubscriptions()
    }, (error) => {
      this.toastr.error(error)
      if (error.status == 401) {
        AuthService.logout()
      }
    });
  }

  isSubscribed(reservationEntityId: any) {
    var retVal = false;
    var mailAddress = localStorage.getItem('mailAddress');

    /*
    for(let subscription of this.subscriptions){
      if(subscription.user.mailAddress == mailAddress && subscription.reservationEntities.id == reservationEntityId){
        retVal = true;
        break;
      }
    }*/

    var filtered = this.subscriptions.filter((subscription: { user: { mailAddress: string | null; }; reservationEntities: { id: any; }; }) => (subscription.user.mailAddress == mailAddress && subscription.reservationEntities.id == reservationEntityId))

    if (filtered.length != 0) {
      retVal = true
    }
    else {
      retVal = false;
    }

    return retVal;
  }

  resetReservationSearch() {
    this.searchFilterSortModel.date = ""
    this.searchFilterSortModel.time = ""
    this.searchFilterSortModel.daysNumber = 0
    this.searchFilterSortModel.guestsNumber = 0
    this.searchFilterSort()
  }

  reserveAditionalServices(id: any) {
    this.router.navigateByUrl('/aditional_services_reservation/' + id +
      '?date=' + this.searchFilterSortModel.date +
      '&time=' + this.searchFilterSortModel.time +
      '&daysNumber=' + this.searchFilterSortModel.daysNumber +
      '&guestsNumber=' + this.searchFilterSortModel.guestsNumber +
      '&isSpecial=false');
  }

  getMarkById(id: any) {
    var mark = this.marks.find((m: { reservationEntitiesId: any; }) => { return m.reservationEntitiesId == id })
    return mark.avgMark
  }

}
