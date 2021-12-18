import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { serverPortApi } from 'src/app/app.consts';
import { AdditionalService } from '../additional-services/AdditionalService';
import { AvailableAppointment } from '../new-available-appointment/AvailableAppointment';
import { Boat } from '../new-boat/Boat';
import { Cottage } from '../new-entity/Cottage';
import { ReservationEntity } from '../new-entity/ReservationEntity';

@Injectable({
  providedIn: 'root'
})
export class EntitiesService {

  constructor(private http: HttpClient) { }

  getEntity(id: any) {
    return this.http.get<any>(serverPortApi + "reservationEntities/get/" + id)
  }

  getAdditionalServices(id: any) {
    return this.http.get<any>(serverPortApi + "reservationEntities/additionalServices/" + id);
  }

  createAdditionalService(additionalService: AdditionalService) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    
    return this.http.post<any>(serverPortApi + "reservationEntities/additionalServices", additionalService, header);
  }

  getEntityImages(id: any) {
    return this.http.get<any>(serverPortApi + "reservationEntities/images/" + id);
  }

  getOneEntityImage(id: any) {
    return this.http.get<any>(serverPortApi + "reservationEntities/oneImage/" + id);
  }

  uploadImage(file: File, entityId: string) {
    let photo = new FormData();
    photo.append('multipartImage', file);

    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }

    return this.http.post<any>(serverPortApi + 'reservationEntities/imageUpload/' + entityId, photo, header);
  }

  getAvailableAppointments(entityId: string) {
    return this.http.get<any>(serverPortApi + "reservationEntities/availableAppointments/" + entityId);
  }

  createAvailableAppointment(availableAppointment: AvailableAppointment) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    
    return this.http.post<any>(serverPortApi + "reservationEntities/availableAppointments", availableAppointment, header);
  }

  // DEO METODA VEZAN ZA VIKENDICE //////////////////////////

  getAllUserCottages() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get<any>(serverPortApi + "cottage/" + localStorage.getItem("mailAddress") + "/allCottages", header)
  }

  createNewCottage(newEntity: ReservationEntity) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    var newCottage = this.createCottageObjectForCreating(newEntity);
    return this.http.post(serverPortApi + 'cottage/create', newCottage, header);
  }

  updateCottage(cottageDTO : any) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    var updatedCottage = this.createCottageObjectForUpdate(cottageDTO);
    return this.http.put<any>(serverPortApi + "cottage/update", updatedCottage, header);
  }

  deleteCottage(id : string) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.delete<any>(serverPortApi + "cottage/" + id, header)
  }

  private createCottageObjectForCreating(entity : ReservationEntity) : Cottage {
    return new Cottage(null, entity.name, entity.numberOfRooms, entity.bedsPerRoom, entity.price, entity.promotionalDescription,
                       entity.rulesOfConduct, entity.street, entity.number, entity.city, entity.postalCode, entity.country,
                       entity.userId, entity.username, null);
  }

  private createCottageObjectForUpdate(entity : ReservationEntity) : Cottage {
    return new Cottage(entity.id, entity.name, entity.numberOfRooms, entity.bedsPerRoom, entity.price, entity.promotionalDescription,
                       entity.rulesOfConduct, entity.street, entity.number, entity.city, entity.postalCode, entity.country,
                       entity.userId, entity.username, entity.addressId);
  }

  ///////////////////////////////////////////////////////////
  
  // DEO METODA VEZAN ZA BRODOVE ////////////////////////////

  getAllUserBoats() {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.get<any>(serverPortApi + "boat/" + localStorage.getItem("mailAddress") + "/allBoats", header)
  }

  createNewBoat(newBoat: Boat) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.post(serverPortApi + 'boat/create', newBoat, header);
  }

  updateBoat(boat : any) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.put<any>(serverPortApi + "boat/update", boat, header);
  }

  deleteBoat(id : string) {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`)
    }
    return this.http.delete<any>(serverPortApi + "boat/" + id, header)
  }

  ///////////////////////////////////////////////////////////

}
