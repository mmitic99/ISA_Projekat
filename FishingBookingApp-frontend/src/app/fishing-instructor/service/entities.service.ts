import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { Class } from '../new-entity/Class';
import { ReservationEntity } from '../new-entity/ReservationEntity';

@Injectable({
  providedIn: 'root'
})
export class EntitiesService {

  constructor(private http: HttpClient) { }

  getEntity(id: any) {
    return this.http.get<any>(serverPortApi+"reservationEntities/get/" + id)
  }

  // DEO METODA VEZAN ZA CASOVE PECANJA //////////////////////////

  getAllUserClasses() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get<any>(serverPortApi + "class/" + localStorage.getItem("mailAddress") + "/allClasses", header)
  }

  createNewClass(newEntity: ReservationEntity) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    var newClass = this.createClassObjectForCreating(newEntity);
    return this.http.post(serverPortApi + 'class/create', newClass, header);
  }

  updateClass(classDTO : any) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    var updatedClass = this.createClassObjectForUpdate(classDTO);
    return this.http.put<any>(serverPortApi + "class/update", updatedClass, header);
  }

  deleteClass(id : string) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.delete<any>(serverPortApi + "class/" + id, header)
  }

  private createClassObjectForCreating(entity : ReservationEntity) : Class {
    return new Class(null, entity.name,  entity.price, entity.promotionalDescription,
                       entity.rulesOfConduct, entity.street, entity.number, entity.city, entity.postalCode, entity.country,
                       entity.userId, entity.username, null, entity.bio, entity.gear, entity.ifCanceled);
  }

  private createClassObjectForUpdate(entity : ReservationEntity) : Class {
    return new Class(null, entity.name,  entity.price, entity.promotionalDescription,
      entity.rulesOfConduct, entity.street, entity.number, entity.city, entity.postalCode, entity.country,
      entity.userId, entity.username, null, entity.bio, entity.gear, entity.ifCanceled);
  }



}
