import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../service/reservation.service';

@Component({
  selector: 'app-complaint',
  templateUrl: './complaint.component.html',
  styleUrls: ['./complaint.component.css']
})
export class ComplaintComponent implements OnInit {

  oldReservation: any;

  constructor(
    private reservationService: ReservationService
  ) { }

  ngOnInit(): void {
    this.getAllOldReservation();
  }

  getAllOldReservation() {
    this.reservationService.getAllOldReservation().subscribe(
      (data)=>{
        this.oldReservation = data;
      },
      (error)=>{
        this.oldReservation = []
      }
    )
  }

}
