import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { serverPortApi } from 'src/app/app.consts';
import { SearchFilterSortModel } from '../home/searchFilterSortModel';

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
}
