import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationService } from 'src/app/regular-user/service/reservation.service';
import { EntitiesService } from '../service/entities.service';
import { SpecialReservationService } from '../service/special-reservation.service';
import { SpecialReservation } from './SpecialReservation';

@Component({
  selector: 'app-special-reservation',
  templateUrl: './special-reservation.component.html',
  styleUrls: ['./special-reservation.component.css']
})
export class SpecialReservationComponent implements OnInit {

  times: string[] = [];
  isNumberOfDaysValid = true;
  isNumberOfPeoplesValid = true;
  isPriceValid = true;

  additionalServices: any
  reservationEntity: any;
  newSpecialReservation = new SpecialReservation("", "", "", "", 1, "", new Array<number>(), "", "", "", "", "");

  constructor(private route: ActivatedRoute, 
              private toastr: ToastrService, 
              private entitiesService: EntitiesService, 
              private specialReservationService: SpecialReservationService,
              private router: Router) { }

  ngOnInit(): void {
    this.newSpecialReservation.mailAddress = localStorage.getItem('mailAddress');
    this.route.params.subscribe(params => {
      this.newSpecialReservation.reservationEntityId = + params['id'];
    })
    this.getTimes();
    this.getAdditionalServices();
    this.getEntity();
  }

  createSpecialReservation() {
    if (this.isAllValid()) {
      this.specialReservationService.createNewSpecialReservation(this.newSpecialReservation).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste kerirali akciju.", "Uspešno kreiranje akcije", { timeOut: 100000 })
          this.router.navigate(['/specialUser/viewEntity/' + this.newSpecialReservation.reservationEntityId])
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

  getEntity() {
    this.entitiesService.getEntity(this.newSpecialReservation.reservationEntityId).subscribe(
      (data) => {
        this.reservationEntity = data
      }
    )
  }

  getAdditionalServices() {
    this.additionalServices = this.entitiesService.getAdditionalServices(this.newSpecialReservation.reservationEntityId).subscribe((data)=>{
      this.additionalServices = data;
    })
  }

  selectAdditionalService(id: number) {
    if (this.newSpecialReservation.additionalServicesId.includes(id)) {
      this.newSpecialReservation.additionalServicesId = this.newSpecialReservation.additionalServicesId.filter(item => item !== id)
    } else {
      this.newSpecialReservation.additionalServicesId.push(id)
    }
  }

  calculatePriceSumForAdditionalServices(){
    let retVal = 0;
    for(let id of this.newSpecialReservation.additionalServicesId){
      var additionalService = this.additionalServices.find((a: { id: number; }) => {return a.id == id})
      retVal += additionalService.price
    }
    return retVal;
  }

  isAllValid(): boolean {
    if (this.isAllFilled() && this.isNumberOfDaysValid && this.isNumberOfPeoplesValid && this.isPriceValid) {
      return true;
    }
    else {
      return false;
    }
  }

  isAllFilled(): boolean {
    if (this.newSpecialReservation.startDate == "" || this.newSpecialReservation.startTime == ""
      || this.newSpecialReservation.days <= 0 || this.newSpecialReservation.maxPeople == ""
      || this.newSpecialReservation.price == "" || this.newSpecialReservation.validFromDate == ""
      || this.newSpecialReservation.validFromTime == "" || this.newSpecialReservation.validToDate == ""
      || this.newSpecialReservation.validToTime == "") {
      return false;
    }

    return true;
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

  onFocusOutNumberOfDays(): void {
    this.isNumberOfDaysValid = this.newSpecialReservation.days > 0;
  }

  onFocusOutNumberOfPeoples(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isNumberOfPeoplesValid = regexp.test(this.newSpecialReservation.maxPeople);
  }

  onFocusOutPrice(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isPriceValid = regexp.test(this.newSpecialReservation.price);
  }
}
