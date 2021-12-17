import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { Reservation } from 'src/app/unauthorized-user/home/reservation';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private http: HttpClient) { }

  reserve(reservation: Reservation) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    reservation.mailAddress = localStorage.getItem('mailAddress');
    return this.http.post(serverPortApi + "reservation/reserve", reservation, header);
  }
}
