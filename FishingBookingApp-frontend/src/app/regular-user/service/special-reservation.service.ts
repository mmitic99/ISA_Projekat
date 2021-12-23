import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';

@Injectable({
  providedIn: 'root'
})
export class SpecialReservationService {

  constructor(private http: HttpClient) { }

  getAllSpecialReservation(id: any) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + "specialReservation/get/" + id, header)
  }

  reserve(specResId: any) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.put(serverPortApi + "specialReservation/take/" + specResId, null, header)
  }
}
