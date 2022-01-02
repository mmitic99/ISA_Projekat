import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { data } from 'jquery';
import { ToastrService } from 'ngx-toastr';
import { EntitiesService } from '../service/entities.service';
import { ReservationForUserServiceService } from '../service/reservation-for-user-service.service';
import { ReservationForUser } from './ReservationForUser';
import { UserForReservation } from './UserForReservation';

@Component({
  selector: 'app-new-reservation-for-user',
  templateUrl: './new-reservation-for-user.component.html',
  styleUrls: ['./new-reservation-for-user.component.css']
})
export class NewReservationForUserComponent implements OnInit {

  times: string[] = [];
  isNumberOfDaysValid = true;
  isNumberOfPeoplesValid = true;
  minDate = new Date();

  additionalServices: any
  reservationEntity: any;
  newReservation = new ReservationForUser("", "", "", "", "", 1, "", new Array<number>(), "");
  user = new UserForReservation("", "", "", "");
  userExist : boolean = false;

  constructor(private route: ActivatedRoute, 
    private toastr: ToastrService, 
    private entitiesService: EntitiesService,
    private reservationForUserService: ReservationForUserServiceService,
    private router: Router) { }

  ngOnInit(): void {
    this.newReservation.ownerMailAddress = localStorage.getItem('mailAddress');
    this.route.params.subscribe(params => {
      this.newReservation.reservationEntityId = + params['id'];
    })
    this.getTimes();
    this.getAdditionalServices();
    this.getEntity();
    this.getUserOfCurrentReservation();
  }

  createReservationForUser() {
    if (this.isAllValid()) {
      this.reservationForUserService.createReservationForUser(this.newReservation).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste kerirali rezervaciju za klijenta.")
          this.router.navigate(['/specialUser/viewEntity/' + this.newReservation.reservationEntityId])
        },
        (error) => {
          this.toastr.error(error.error.message)
        }
      )
    }
    else {
      this.toastr.error("Nisu popunjena sva polja ili su neka nevalidno unesena.")
    }
  }

  selectAdditionalService(id: number) {
    if (this.newReservation.additionalServicesId.includes(id)) {
      this.newReservation.additionalServicesId = this.newReservation.additionalServicesId.filter(item => item !== id)
    } else {
      this.newReservation.additionalServicesId.push(id)
    }
  }

  calculatePriceSumForAdditionalServices(){
    let retVal = 0;
    for(let id of this.newReservation.additionalServicesId){
      var additionalService = this.additionalServices.find((a: { id: number; }) => {return a.id == id})
      retVal += additionalService.price
    }
    return retVal;
  }

  getEntity() {
    this.entitiesService.getEntity(this.newReservation.reservationEntityId).subscribe(
      (data) => {
        this.reservationEntity = data
      }
    )
  }

  getUserOfCurrentReservation() {
    this.entitiesService.getCurrentReservation(this.newReservation.reservationEntityId).subscribe(
      (data) => {
        if (data != null) {
          this.user = new UserForReservation(data.user.name, data.user.surname, data.user.mobileNumber, data.user.mailAddress);
          this.userExist = true;
          this.newReservation.userMailAddress = data.user.mailAddress;
        } else {
          this.userExist = false;
        }
      }
    )
  }

  getAdditionalServices() {
    this.additionalServices = this.entitiesService.getAdditionalServices(this.newReservation.reservationEntityId).subscribe((data)=>{
      this.additionalServices = data;
    })
  }

  getTimes() {
    for (var i = 0; i < 24; i++) {
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

  isAllValid(): boolean {
    if (this.isAllFilled() && this.isNumberOfDaysValid && this.isNumberOfPeoplesValid) {
      return true;
    }
    else {
      return false;
    }
  }

  isAllFilled(): boolean {
    if (this.newReservation.startDate == "" || this.newReservation.startTime == ""
      || this.newReservation.days <= 0 || this.newReservation.maxPeople == "") {
      return false;
    }

    return true;
  }

  onFocusOutNumberOfDays(): void {
    this.isNumberOfDaysValid = this.newReservation.days > 0;
  }

  onFocusOutNumberOfPeoples(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isNumberOfPeoplesValid = regexp.test(this.newReservation.maxPeople);
  }

}
