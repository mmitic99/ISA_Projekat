import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { ReservationForUser } from '../new-reservation-for-user/ReservationForUser';

@Injectable({
  providedIn: 'root'
})
export class ReservationForUserServiceService {

  constructor(private http: HttpClient) { }

  createReservationForUser(newReservation : ReservationForUser) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.post(serverPortApi + 'reservation/reserveForClient', newReservation, header);
  }
}
