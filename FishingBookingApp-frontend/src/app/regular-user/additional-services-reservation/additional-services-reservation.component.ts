import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Reservation } from 'src/app/unauthorized-user/home/reservation';
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

  constructor(private activatedRoute: ActivatedRoute,
    private reservationService: ReservationService,
    private toastr: ToastrService
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
    
    this.reservation = new Reservation(this.date, this.time, this.days, this.guests, this.id);

  }
  getAdditionalServices() {
    this.reservationService.getAdditionalServiceByEntityId(this.id).subscribe((data)=>{
      this.additionalServices = data;
    },(error)=>{

    })
  }

  reserveEntity() {
    this.reservationService.reserve(this.reservation).subscribe((data) => {

    }, (error) => {
      this.toastr.error(error.error.error)
    })
  }

  selectAdditionalService(type: number) {
    if (this.reservation.additionalServicesId.includes(type)) {
      this.reservation.additionalServicesId = this.reservation.additionalServicesId.filter(item => item !== type)
    } else {
      this.reservation.additionalServicesId.push(type)
    }
  }
}
