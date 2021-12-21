import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Reservation } from 'src/app/unauthorized-user/home/reservation';
import { AuthService } from 'src/app/unauthorized-user/service/auth.service';
import { ReservationEntitiesService } from 'src/app/unauthorized-user/service/reservation-entities.service';
import { ReservationService } from '../service/reservation.service';

@Component({
  selector: 'app-additional-services-reservation',
  templateUrl: './additional-services-reservation.component.html',
  styleUrls: ['./additional-services-reservation.component.css']
})
export class AdditionalServicesReservationComponent implements OnInit {
  id: any;
  date = "";
  time = "";
  days = 0;
  guests = 0;
  additionalServices:any
  reservation = new Reservation()
  reservationEntity: any

  constructor(private activatedRoute: ActivatedRoute,
    private reservationService: ReservationService,
    private toastr: ToastrService,
    private reservationEntitiesService: ReservationEntitiesService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.id = params.id;
    })
    this.activatedRoute.queryParams.subscribe(params => {
      this.date = params.date;
      this.time = params.time;
      this.days = params.daysNumber;
      this.guests = params.guestsNumber;
    });
    this.getAdditionalServices()
    this.getEntity()

    this.reservation = new Reservation(this.date, this.time, this.days, this.guests, this.id);

  }

  getEntity() {
    this.reservationEntitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.reservationEntity = data
      },
      (error)=>{
        if(error.status == 401){
          AuthService.logout()
        }}
    )
  }
  getAdditionalServices() {
    this.reservationService.getAdditionalServiceByEntityId(this.id).subscribe((data)=>{
      this.additionalServices = data;
    },(error)=>{
      if(error.status == 401){
        AuthService.logout()
      }
    })
  }

  reserveEntity() {
    this.reservationService.reserve(this.reservation).subscribe((data) => {
      this.router.navigate(["/curent_reservation"])
      this.toastr.success("Uspešno ste rezervisali. Uskoro će vam na mejl stići potvrda registracije.")
    }, (error) => {
      this.toastr.error(error.error.error)
      if(error.status == 401){
        AuthService.logout()
      }
    })
  }

  selectAdditionalService(id: number) {
    if (this.reservation.additionalServicesId.includes(id)) {
      this.reservation.additionalServicesId = this.reservation.additionalServicesId.filter(item => item !== id)
    } else {
      this.reservation.additionalServicesId.push(id)
    }
  }

  calculatePriceSumForAdditionalServices(){
    let retVal = 0;
    for(let id of this.reservation.additionalServicesId){
      var additionalService = this.additionalServices.find((a: { id: number; }) => {return a.id == id})
      retVal += additionalService.price
    }
    return retVal;
  }
}
