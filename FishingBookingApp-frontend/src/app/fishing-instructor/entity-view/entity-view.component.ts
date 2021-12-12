import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationEntity } from '../new-entity/ReservationEntity';
import { EntitiesService } from '../service/entities.service';

@Component({
  selector: 'app-entity-view',
  templateUrl: './entity-view.component.html',
  styleUrls: ['./entity-view.component.css']
})
export class EntityViewComponent implements OnInit {

  reservationEntity = new ReservationEntity("", "", "", "", "", "", "", "", "", "", "", "", "", "", "","");
  isPostalCodeValid = true;
  userRole: any;
  private id: any;
  constructor(private route: ActivatedRoute, private entitiesService: EntitiesService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    this.reservationEntity.userId = localStorage.getItem('userId');
    this.reservationEntity.username = localStorage.getItem('mailAddress');
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();;
  }

  getEntity() {
    this.entitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.reservationEntity = new ReservationEntity(data.id, data.name, data.price,
          data.promotionalDescription, data.rulesOfConduct, data.address.street,
          data.address.number, data.address.city, data.address.postalCode,
          data.address.country, this.reservationEntity.userId, this.reservationEntity.username, data.address.address_id, data.bio, data.gear, data.ifCanceled);
      }
    )
  }

  updateCottage() {
    if (this.isAllValid()) {
      this.entitiesService.updateClass(this.reservationEntity).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste izmenili čas.", "Uspešna izmena časa", { timeOut: 100000 })
        },
        (error) => {
          this.toastr.error("Neuspešna izmena časa")
        }
      );
    }
    else {
      this.toastr.error("Nisu popunjena sva polja.")
    }
  }

  deleteCottage() {
    this.entitiesService.deleteClass(this.reservationEntity.id).subscribe(
      () => {
        this.toastr.success("Uspešno ste izbisali čas.", "", { timeOut: 100000 })
      },
      () => {
        this.toastr.error("Neuspešno brisanje časa")
      }
    )
  }

  updateBoat() {

  }

  isAllValid(): boolean {
    if (this.isAllFilled() && this.isPostalCodeValid) {
      return true;
    }
    else {
      return false;
    }
  }

  isAllFilled(): boolean {
    // Provera polja koja poseduju svi entiteti
    if (this.reservationEntity.name == "" || this.reservationEntity.promotionalDescription == ""
      || this.reservationEntity.rulesOfConduct == "" || this.reservationEntity.street == ""
      || this.reservationEntity.number == "" || this.reservationEntity.city == ""
      || this.reservationEntity.postalCode == "" || this.reservationEntity.country == "") {
      return false;
    }



    return true;
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.reservationEntity.postalCode);
  }

}
