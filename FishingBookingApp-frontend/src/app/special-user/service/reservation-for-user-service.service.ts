import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { ReservationForUser } from '../new-reservation-for-user/ReservationForUser';
import { Report } from '../reservation-view/Report';

@Injectable({
  providedIn: 'root'
})
export class ReservationForUserServiceService {

  constructor(private http: HttpClient) { }

  getReservationById(reservationId : string) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + 'reservation/' + reservationId, header);
  }

  createReservationForUser(newReservation : ReservationForUser) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.post(serverPortApi + 'reservation/reserveForClient', newReservation, header);
  }

  createReportForReservation(report : Report) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.post(serverPortApi + 'reportForReservation', report, header);
  }

  getReportForReservation(reservationId : string) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get<any>(serverPortApi + 'reportForReservation/' + reservationId, header);
  }
}
