import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';
import { Subscription } from '../subscribed/Subscription';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private http: HttpClient) { }

  getSubsriptions() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + 'subscription/subscribedReservationEntities', header);
  }

  reservationEntitySubscription(subscription: Subscription) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    subscription.mailAddress = localStorage.getItem('mailAddress')
    return this.http.put(serverPortApi + 'subscription/subscription', subscription, header)
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

    return this.http.get<any>(serverPortApi + "subscription/searchFilterSort", { params: params, headers: header })
  
  }
  
  checkSubscriptionForReservationEntityId(id: any) {
    var mailAddress = localStorage.getItem('mailAddress')
    if(mailAddress == null){
      mailAddress = ""
    }
    var header = new HttpHeaders()
      .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    var params = new HttpParams();
    params = params.append('mailAddress', mailAddress);
    params = params.append('reservationEntityId', id);

    return this.http.get<any>(serverPortApi + "subscription/getByUserAndEntity", { params: params, headers: header })
  }
}
