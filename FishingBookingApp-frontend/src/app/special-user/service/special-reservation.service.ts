import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SpecialReservation } from '../special-reservation/SpecialReservation';
import { serverPortApi } from 'src/app/app.consts';

@Injectable({
  providedIn: 'root'
})
export class SpecialReservationService {

  constructor(private http: HttpClient) { }

  createNewSpecialReservation(newSpecialReservation : SpecialReservation) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.post(serverPortApi + 'specialReservation', newSpecialReservation, header);
  }
}
