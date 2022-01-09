import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { Review } from '../reservation/Review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }

  getAllReservationIdInReviews() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + "review/getReservationIdInReviewForMailAddress/" + localStorage.getItem('mailAddress'), header);
  }
  
  saveReview(review: Review) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    review.mailAddress = localStorage.getItem('mailAddress')
    return this.http.post(serverPortApi + "review/createReview", review, header);
  }

  getAllReviews(id: any) {
    return this.http.get(serverPortApi + "review/getAllForReservationEntities/" + id);
  }
}
