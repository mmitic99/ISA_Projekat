import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReservationEntitiesService {
  

  constructor(private http: HttpClient) { }

  getAllReservationEntities() {
    return this.http.get<any>("http://localhost:8080/api/reservationEntities/getAll")
  }
  
  getEntity(id: any) {
    return this.http.get<any>("http://localhost:8080/api/reservationEntities/get/1")
  }
  
}
