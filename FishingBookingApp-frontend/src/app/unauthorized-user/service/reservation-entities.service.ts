import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { serverPortApi } from 'src/app/app.consts';

@Injectable({
  providedIn: 'root'
})
export class ReservationEntitiesService {
  

  constructor(private http: HttpClient) { }

  getAllReservationEntities() {
    return this.http.get<any>(serverPortApi+"reservationEntities/getAll")
  }
  
  getEntity(id: any) {
    return this.http.get<any>(serverPortApi+"reservationEntities/get/1")
  }
  
}
