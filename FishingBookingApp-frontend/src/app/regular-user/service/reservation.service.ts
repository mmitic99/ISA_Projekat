import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { Reservation } from 'src/app/unauthorized-user/home/reservation';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';

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

  searchFilterSort(searchFilterSortModel: SearchFilterSortModel) {
    var header = new HttpHeaders()
      .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    var params = new HttpParams();
    params = params.append('sort', searchFilterSortModel.sort);

    if (searchFilterSortModel.types.length != 0) {
      for (let type of searchFilterSortModel.types) {
        params = params.append('types', type);
      }
    }
    else {
      params = params.append('types', '');
    }
    params = params.append('search', searchFilterSortModel.search);
    var mail: any
    if(localStorage.getItem("mailAddress") != null){
      mail = localStorage.getItem("mailAddress");
    }
    params = params.append('mailAddress', mail);

    return this.http.get<any>(serverPortApi + "reservation/searchFilterSort/" + localStorage.getItem('mailAddress'), { params: params, headers: header })
  }
}
