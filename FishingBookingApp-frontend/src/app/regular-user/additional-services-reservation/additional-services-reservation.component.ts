import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Reservation } from 'src/app/unauthorized-user/home/reservation';
import { AuthService } from 'src/app/unauthorized-user/service/auth.service';
import { ReservationEntitiesService } from 'src/app/unauthorized-user/service/reservation-entities.service';
import { ReservationService } from '../service/reservation.service';
import { SpecialReservationService } from '../service/special-reservation.service';

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
  additionalServices: any
  reservation = new Reservation()
  reservationEntity: any
  isSpecial: boolean | undefined
  additionalServicesIds: any
  price: any
  specResId: any

  constructor(private activatedRoute: ActivatedRoute,
    private reservationService: ReservationService,
    private toastr: ToastrService,
    private reservationEntitiesService: ReservationEntitiesService,
    private router: Router,
    private specialReservationService: SpecialReservationService
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
      if (params.isSpecial == "false") {
        this.isSpecial = false;
      }
      else {
        this.isSpecial = true;
      }
      this.price = params.price;
      this.specResId = params.specResId;
    });
    this.activatedRoute.queryParamMap.subscribe(params => this.additionalServicesIds = params.getAll('addSerIds'));

    this.getAdditionalServices()
    this.getEntity()

    this.reservation = new Reservation(this.date, this.time, this.days, this.guests, this.id);


  }
  selectSpecialAdditionalService() {
    for (var addId of this.additionalServicesIds) {
      this.selectAdditionalService(parseInt(addId))
    }
  }

  getEntity() {
    this.reservationEntitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.reservationEntity = data
      },
      (error) => {
        if (error.status == 401) {
          AuthService.logout()
        }
      }
    )
  }
  getAdditionalServices() {
    this.reservationService.getAdditionalServiceByEntityId(this.id).subscribe((data) => {
      this.additionalServices = data;
      if (this.isSpecial) {
        this.selectSpecialAdditionalService()
      }
    }, (error) => {
      if (error.status == 401) {
        AuthService.logout()
      }
    })
  }

  reserveEntity() {
    if (!this.isSpecial) {
      this.reservationService.reserve(this.reservation).subscribe((data) => {
        this.router.navigate(["/curent_reservation"])
        this.toastr.success("Uspešno ste rezervisali. Uskoro će vam na mejl stići potvrda rezervacije.")
      }, (error) => {
        this.toastr.error(error.error.message)
        if (error.status == 401) {
          AuthService.logout()
        }
      })
    }
    else {
      this.specialReservationService.reserve(this.specResId).subscribe((data) => {
        this.router.navigate(["/curent_reservation"])
        this.toastr.success("Uspešno ste rezervisali akciju. Uskoro će vam na mejl stići potvrda rezervacije.")
      }, (error) => {
        this.toastr.error(error.error.message)
        if (error.status == 401) {
          AuthService.logout()
        }
      })
    }
  }

  selectAdditionalService(id: number) {
    if (this.reservation.additionalServicesId.includes(id)) {
      this.reservation.additionalServicesId = this.reservation.additionalServicesId.filter(item => item !== id)
    } else {
      this.reservation.additionalServicesId.push(id)
    }
  }

  calculatePriceSumForAdditionalServices() {
    let retVal = 0;
    for (let id of this.reservation.additionalServicesId) {
      var additionalService = this.additionalServices.find((a: { id: number; }) => { return a.id == id })
      retVal += additionalService.price
    }
    return retVal;
  }

  getAddServName(id: any) {
    return this.additionalServices.find((addSer: { id: any; }) => { return addSer.id == id }).name
  }

  getAddServPrice(id: any) {
    return this.additionalServices.find((addSer: { id: any; }) => { return addSer.id == id }).price
  }
}
