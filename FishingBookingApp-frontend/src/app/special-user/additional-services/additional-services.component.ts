import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationEntity } from '../new-entity/ReservationEntity';
import { EntitiesService } from '../service/entities.service';
import { AdditionalService } from './AdditionalService';

@Component({
  selector: 'app-additional-services',
  templateUrl: './additional-services.component.html',
  styleUrls: ['./additional-services.component.css']
})
export class AdditionalServicesComponent implements OnInit {

  private entityId : any;
  reservationEntity = new ReservationEntity("", "", "", "", "", "", "", "", "", "", "", "", 0, 0, "", "", "");
  additionalServices : any;
  creatingAdditionalService : boolean = false;
  newAdditionalService = new AdditionalService("", "", "", "");
  isPriceValid = true;
  constructor(private route: ActivatedRoute, private entitiesService: EntitiesService, private toastr: ToastrService) { }

  ngOnInit(): void {
    //this.userRole = localStorage.getItem('role');
    this.reservationEntity.userId = localStorage.getItem('userId');
    this.reservationEntity.username = localStorage.getItem('mailAddress');
    this.route.params.subscribe(params => {
      this.entityId = + params['id'];
    })
    this.newAdditionalService.reservationEntityId = this.entityId;
    this.getEntity();
    this.getAdditionalServices();
  }

  getAdditionalServices() {
    this.entitiesService.getAdditionalServices(this.entityId).subscribe(
      (data) => {
        this.additionalServices = data;
      }
    )
  }

  getEntity() {
    this.entitiesService.getEntity(this.entityId).subscribe(
      (data) => {
        this.reservationEntity = new ReservationEntity(data.id, data.name, data.numberOfRooms, data.bedsPerRoom, data.price,
          data.promotionalDescription, data.rulesOfConduct, data.address.street,
          data.address.number, data.address.city, data.address.postalCode,
          data.address.country, data.address.longitude, data.address.latitude, this.reservationEntity.userId, this.reservationEntity.username, data.address.address_id);
      }
    )
  }

  enableCreating() {
    this.creatingAdditionalService = true;
  }

  disableCreating() {
    this.creatingAdditionalService = false;
  }

  createAdditionalService() {
    if (this.isAllValid()) {
      this.entitiesService.createAdditionalService(this.newAdditionalService).subscribe(
        (data) => {
          this.toastr.success('Uspešno ste dodali dodatnu uslugu.');
          this.additionalServices.push(data);
          this.newAdditionalService.name = "";
          this.newAdditionalService.description = "";
          this.newAdditionalService.price = "";
          this.creatingAdditionalService = false;
        },
        () => {
          this.toastr.error("Neuspešno kreiranje usluge.");
        }
      )
    }
    else {
      this.toastr.error("Nisu popunjena sva polja ili su neka nevalidno unesena.")
    }
  }

  isAllValid(): boolean {
    if (this.isAllFilled() && this.isPriceValid) {
      return true;
    }
    else {
      return false;
    }
  }

  isAllFilled(): boolean {
    if (this.newAdditionalService.name == "" || this.newAdditionalService.description == ""
        || this.newAdditionalService.price == "") {
      return false;
    }
    return true;
  }

  onFocusOutPrice(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isPriceValid = regexp.test(this.newAdditionalService.price);
  }
}
