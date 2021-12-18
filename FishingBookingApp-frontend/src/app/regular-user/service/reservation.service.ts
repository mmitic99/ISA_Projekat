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

  getAdditionalServiceByEntityId(id: any) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + "reservation/getAdditionalServices/" + id,  header);
  }

  getCurrentReservation() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + "reservation/getCurrentReservation/" + localStorage.getItem('mailAddress'),  header);
  }

  cancelReservation(dto: any) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    dto.mailAddress = localStorage.getItem('mailAddress')
    return this.http.put(serverPortApi + "reservation/cancelReservation/" , dto,  header)
  }
  getAllOldReservation() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + "reservation/getAllOldReservation/" + localStorage.getItem('mailAddress'),  header);
  }
}
