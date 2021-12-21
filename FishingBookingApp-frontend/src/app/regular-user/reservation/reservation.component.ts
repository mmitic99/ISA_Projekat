import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';
import { AuthService } from 'src/app/unauthorized-user/service/auth.service';
import { ReservationService } from '../service/reservation.service';
import { ReviewService } from '../service/review.service';
import { Review } from './Review';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  searchFilterSortModel = new SearchFilterSortModel("")
  reservations: any;
  reservationIdsInReviews: any;
  review = new Review()
  possibleMarks = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  constructor(
    private reservationService: ReservationService,
    private reviewService: ReviewService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.review = new Review()
    this.getAllOldReservation();
    this.getAllReservationIdInReviews();
  }
  getAllReservationIdInReviews() {
    this.reviewService.getAllReservationIdInReviews().subscribe(
      (data) => {
        this.reservationIdsInReviews = data
      },
      (error) => { this.reservationIdsInReviews = []
        if(error.status == 401){
          AuthService.logout()
        } })
  }

  getAllOldReservation() {
    this.reservationService.getAllOldReservation().subscribe(
      (data) => {
        this.reservations = data
        for (let reservation of this.reservations) {
          let dateTime = reservation.start
          reservation.start = new Date(dateTime[0], dateTime[1] - 1, dateTime[2], dateTime[3], dateTime[4])
          this.setModals()
        }
      },
      (error) => { this.reservations = []
        if(error.status == 401){
          AuthService.logout()
        }
      })
  }
  setModals() {
    for (let reservation of this.reservations) {
      reservation.isModalActive = false;
    }
  }

  toggleModal(reservation: any) {
    reservation.isModalActive = !reservation.isModalActive;
    this.review.reservationId = reservation.id;
  }

  canReview(reservation: any) {
    let checkDate = new Date(reservation.start)
    checkDate.setHours(reservation.start.getHours() + reservation.durationInHours)
    return checkDate < new Date() && !this.reservationIdsInReviews.includes(reservation.id)
  }

  saveReview(reservation: any) {
    if (this.review.mark != 0) {
      this.reviewService.saveReview(this.review).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste ocenili rezervaciju")
          this.ngOnInit()
        },
        (error) => {
          this.toastr.error("Desila se greška prilikom slanja ocene")
          if(error.status == 401){
            AuthService.logout()
          }
        }
      )
    }
    else {
      this.toastr.error("Izaberite ocenu")
    }
  }
}
