import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { Subscription } from '../subscribed/Subscription';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private http: HttpClient) { }

  getSubsription() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + 'subscription/subscribedReservationEntities', header);
  }

  unsubscribeReservationEntity(subscription: Subscription) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    subscription.mailAddress = localStorage.getItem('mailAddress')
    return this.http.put(serverPortApi + 'subscription/subscription', subscription, header)
  }
}
