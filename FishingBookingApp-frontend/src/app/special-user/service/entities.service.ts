import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';

@Injectable({
  providedIn: 'root'
})
export class EntitiesService {

  constructor(private http: HttpClient) { }

  getAllUserCottages() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get<any>(serverPortApi + "cottage/" + localStorage.getItem("mailAddress") + "/allCottages", header)
  }
  
  getAllUserBoats() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get<any>(serverPortApi + "boat/" + localStorage.getItem("mailAddress") + "/allBoats", header)
  }
}
