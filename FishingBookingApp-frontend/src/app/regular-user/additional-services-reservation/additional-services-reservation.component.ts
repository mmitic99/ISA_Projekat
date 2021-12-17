import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-additional-services-reservation',
  templateUrl: './additional-services-reservation.component.html',
  styleUrls: ['./additional-services-reservation.component.css']
})
export class AdditionalServicesReservationComponent implements OnInit {
  id: any;
  date="";
  time="";
  days=0;
  guests=0;

  constructor(private activatedRoute: ActivatedRoute) { }

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

  }

}
