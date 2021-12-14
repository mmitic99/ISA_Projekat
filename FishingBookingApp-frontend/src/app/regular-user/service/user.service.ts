import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { RequestForDeleting } from '../request-for-deleting-account/RequestForDeleting';
import { Subscription } from '../subscribed/Subscription';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getProfile() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get(serverPortApi + 'users/getUser', header);
  }
  
  editProfile(editUser: any) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.put(serverPortApi + 'users/editUser',editUser, header);
  }
  
  sendRequestForDeletingAccount(requestForDeleting: RequestForDeleting) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    requestForDeleting.mailAddress = localStorage.getItem('mailAddress')
    return this.http.post(serverPortApi + 'users/deletingRequest', requestForDeleting, header);
  }

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
