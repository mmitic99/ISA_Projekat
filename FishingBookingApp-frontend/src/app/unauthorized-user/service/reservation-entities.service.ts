import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { serverPortApi } from 'src/app/app.consts';
import { SearchFilterSortModel } from '../home/searchFilterSortModel';
import { Complaint } from 'src/app/regular-user/complaint/Complaint';

@Injectable({
  providedIn: 'root'
})
export class ReservationEntitiesService {
 
  constructor(private http: HttpClient) { }

  getAllReservationEntities() {
    return this.http.get<any>(serverPortApi + "reservationEntities/getAll")
  }

  getEntity(id: any) {
    return this.http.get<any>(serverPortApi + "reservationEntities/get/" + id)
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

    return this.http.get<any>(serverPortApi + "reservationEntities/searchFilterSort/", { params: params, headers: header })
  }

  getEntityImages(id: any) {
    return this.http.get<any>(serverPortApi + "reservationEntities/images/" + id);
  }

  getOneEntityImage(id: any) {
    return this.http.get<any>(serverPortApi + "reservationEntities/oneImage/" + id);
  }

  checkReservation(searchFilterSortModel: SearchFilterSortModel) {
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

    params = params.append('date', searchFilterSortModel.date);
    params = params.append('time', searchFilterSortModel.time);
    params = params.append('days', searchFilterSortModel.daysNumber);
    params = params.append('guests', searchFilterSortModel.guestsNumber);

    return this.http.get<any>(serverPortApi + "reservation/checkReservation", { params: params, headers: header })
  
  }

  getPossibleReservationEntitiesForComplaint() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + "reservationEntities/getPossibleReservationEntitiesForComplaint/" + localStorage.getItem('mailAddress'),  header);
  }

  sendComplain(complaint: Complaint) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    complaint.mailAddress = localStorage.getItem('mailAddress')
    return this.http.post(serverPortApi + 'reservationEntities/addComplaint', complaint, header)
  }

  getMarks() {
    return this.http.get(serverPortApi + "reservationEntities/getMarksForReservationEntities")
  }
}
