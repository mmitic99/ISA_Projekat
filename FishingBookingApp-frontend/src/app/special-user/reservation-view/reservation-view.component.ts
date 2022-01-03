import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReservationForUserServiceService } from '../service/reservation-for-user-service.service';
import { Location } from '@angular/common';
import { Report } from './Report';

@Component({
  selector: 'app-reservation-view',
  templateUrl: './reservation-view.component.html',
  styleUrls: ['./reservation-view.component.css']
})
export class ReservationViewComponent implements OnInit {

  @ViewChild('reportView')
  public reportView!: ElementRef;

  reservation: any;
  reservationId: any;
  priceOfAdditionalServicesPerDay: number = 0;
  reservationPast: boolean = false;
  report : Report = new Report();
  constructor(private route: ActivatedRoute, private reservationService: ReservationForUserServiceService, private _location: Location) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.reservationId = + params['id'];
    })
    this.getReservation();
    this.calculatePriceForAdditionalServicesPerDay();
  }

  getReservation() {
    this.reservationService.getReservationById(this.reservationId).subscribe(
      (data) => {
        this.reservation = data;
        let dateTime = this.reservation.start;
        this.reservation.start = new Date(dateTime[0], dateTime[1] - 1, dateTime[2], dateTime[3], dateTime[4]);
        this.isReservationPast();
      }
    )
  }

  calculatePriceForAdditionalServicesPerDay() {
    let retVal = 0;
    for (let additionalService of this.reservation.additionalServices) {
      retVal += additionalService.price
    }
    return retVal;
  }

  isReservationPast() {
    let reservationEndTime = new Date(new Date(this.reservation.start).setHours(this.reservation.start.getHours() + this.reservation.durationInHours));
    if (reservationEndTime.getTime() < Date.now()) {
      this.reservationPast = true;
    }
    else {
      this.reservationPast = false;
    }
  }

  createReport() {

  }

  cbRequestForPenalty() {
    this.report.requestForPenalty = !this.report.requestForPenalty;
  }

  cbCustomerAppeared() {
    this.report.customerAppeared = !this.report.customerAppeared;
    if (!this.report.requestForPenalty) {
      this.report.requestForPenalty = !this.report.requestForPenalty;
    }
  }

  public moveToReportView():void {
    this.reportView.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'end' });
}

  backClicked() {
    this._location.back();
  }
}
