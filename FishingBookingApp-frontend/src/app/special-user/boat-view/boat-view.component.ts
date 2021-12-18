import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Boat } from '../new-boat/Boat';
import { EntitiesService } from '../service/entities.service';

@Component({
  selector: 'app-boat-view',
  templateUrl: './boat-view.component.html',
  styleUrls: ['./boat-view.component.css']
})
export class BoatViewComponent implements OnInit {

  isBoatLengthValid = true;
  isNumberOfEnginesValid = true;
  isEnginePowerValid = true;
  isMaxSpeedValid = true;
  isCapacityValid = true;
  isPriceValid = true;
  isPostalCodeValid = true;
  boat = new Boat("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
  image : any;
  private id: any;
  imageObject: Array<object> = [];
  constructor(private route: ActivatedRoute, private entitiesService: EntitiesService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.boat.boatOwnerId = localStorage.getItem('userId');
    this.boat.boatOwnerUsername = localStorage.getItem('mailAddress');
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getBoat();
    this.getBoatImages();
  }

  getBoat() {
    this.entitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.boat = new Boat(data.id, data.name, data.boatType, data.boatLength, data.numberOfEngines,
          data.enginePower, data.maxSpeed, data.navigationEquipment, data.fishingEquipment, data.capacity, 
          data.cancellationConditions, data.price, data.promotionalDescription, data.rulesOfConduct, data.address.street,
          data.address.number, data.address.city, data.address.postalCode,
          data.address.country, this.boat.boatOwnerId, this.boat.boatOwnerUsername, data.address.address_id);
      }
    )
  }

  getBoatImages() {
    this.entitiesService.getEntityImages(this.id).subscribe(
      (data) => {
        if (data.length > 0) {
          for (let base64Image of data) {
            let img = 'data:image/jpg;base64,' + base64Image;
            this.imageObject.push({ image: img, thumbImage: img });
          }
        }
      }
    )
  }

  updateBoat() {
    if (this.isAllValid()) {
      this.entitiesService.updateBoat(this.boat).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste izmenili brod/čamac.", "Uspešna izmena broda/čamca", { timeOut: 100000 })
        },
        (error) => {
          this.toastr.error("Neuspešna izmena broda/čamca")
        }
      );
    }
    else {
      this.toastr.error("Nisu popunjena sva polja ili su neka nevalidno unesena.")
    }
  }

  deleteBoat() {
    this.entitiesService.deleteBoat(this.boat.id).subscribe(
      () => {
        this.toastr.success("Uspešno ste izbisali brod/čamac.", "", { timeOut: 100000 })
      },
      () => {
        this.toastr.error("Neuspešno brisanje broda/čamca")
      }
    )
  }

  uploadImage(ev: Event) {
    const target = ev.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];
    const size = file.size;
    if (size >= 1048576) {
      this.toastr.error("Veličina slike je prevelika, maksimalna velicina je 1MB");
      return;
    }
    this.entitiesService.uploadImage(file, this.boat.id).subscribe(
      (data) => {
        this.toastr.success("Uspešno ste postavili sliku.")
        let img = 'data:image/jpg;base64,' + data.base64Image;
        this.imageObject.unshift({ image: img, thumbImage: img })
      },
      () => {
        this.toastr.error("Neuspešno postavljanje slike")
      }
    );
  }

  isAllValid(): boolean {
    if (this.isAllFilled() && this.isBoatLengthValid && this.isNumberOfEnginesValid && this.isEnginePowerValid && this.isMaxSpeedValid && this.isCapacityValid && this.isPriceValid) {
      return true;
    }
    else {
      return false;
    }
  }

  isAllFilled(): boolean {
    // Provera polja koja poseduju svi entiteti
    if (this.boat.name == "" || this.boat.promotionalDescription == ""
      || this.boat.rulesOfConduct == "" || this.boat.street == "" 
      || this.boat.number == "" || this.boat.city == ""
      || this.boat.postalCode == "" || this.boat.country == ""
      || this.boat.boatType == "" || this.boat.boatLength == ""
      || this.boat.numberOfEngines == "" || this.boat.enginePower == ""
      || this.boat.maxSpeed == "" || this.boat.navigationEquipment == ""
      || this.boat.fishingEquipment == "" || this.boat.capacity == ""
      || this.boat.cancellationConditions == "" || this.boat.price == "") {
      return false;
    }

    return true;
  }

  onFocusOutBoatLength(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isBoatLengthValid = regexp.test(this.boat.boatLength);
  }

  onFocusOutNumberOfEngines(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isNumberOfEnginesValid = regexp.test(this.boat.numberOfEngines);
  }

  onFocusOutEnginePower(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isEnginePowerValid = regexp.test(this.boat.enginePower);
  }

  onFocusOutMaxSpeed(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isMaxSpeedValid = regexp.test(this.boat.maxSpeed);
  }

  onFocusOutCapacity(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isCapacityValid = regexp.test(this.boat.capacity);
  }

  onFocusOutPrice(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isPriceValid = regexp.test(this.boat.price);
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.boat.postalCode);
  }

  freeAppointmentsForReservation(): void {
    this.toastr.show("Bice implementirano sa tackom 3.13");
  }

  createNewAction(): void {
    this.toastr.show("Bice implementirano sa tackom 3.16");
  }
  createNewReservationForClient(): void {
    this.toastr.show("Bice implementirano sa tackom 3.22");
  }
}
