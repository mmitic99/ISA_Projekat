import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { serverPortApi } from 'src/app/app.consts';
import { Cottage } from '../new-entity/Cottage';
import { ReservationEntity } from '../new-entity/ReservationEntity';

@Injectable({
  providedIn: 'root'
})
export class NewEntityService {

  constructor(private http: HttpClient) { }

  createNewCottage(newEntity: ReservationEntity) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    var newCottage = this.createCottageObject(newEntity);
    return this.http.post(serverPortApi + 'cottage/create', newCottage, header);
  }

  private createCottageObject(entity : ReservationEntity) : Cottage {
    return new Cottage(entity.name, entity.numberOfRooms, entity.bedsPerRoom, entity.price, entity.promotionalDescription,
                       entity.rulesOfConduct, entity.street, entity.number, entity.city, entity.postalCode, entity.country,
                       entity.userId, entity.username);
  }
}
