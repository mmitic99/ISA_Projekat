import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EntitiesService } from '../service/entities.service';
import { Boat } from './Boat';

@Component({
  selector: 'app-new-boat',
  templateUrl: './new-boat.component.html',
  styleUrls: ['./new-boat.component.css']
})
export class NewBoatComponent implements OnInit {

  isBoatLengthValid = true;
  isNumberOfEnginesValid = true;
  isEnginePowerValid = true;
  isMaxSpeedValid = true;
  isCapacityValid = true;
  isPriceValid = true;
  isPostalCodeValid = true;
  newBoat = new Boat("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
  constructor(private entityService: EntitiesService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.newBoat.boatOwnerId = localStorage.getItem('userId');
    this.newBoat.boatOwnerUsername = localStorage.getItem('mailAddress');
  }

  createBoat(): void {
    if (this.isAllValid()) {
      this.entityService.createNewBoat(this.newBoat).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste kerirali brod/čamac.", "Uspešno kreiranje broda/čamca", { timeOut: 100000 })
          this.router.navigate(['/specialUser'])
        },
        (error) => {
          this.toastr.error("Neuspešno kreiranje broda/čamca")
        }
      )
    }
    else {
      this.toastr.error("Nisu popunjena sva polja ili su neka nevalidno unesena.")
    }
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
    if (this.newBoat.name == "" || this.newBoat.promotionalDescription == ""
      || this.newBoat.rulesOfConduct == "" || this.newBoat.street == "" 
      || this.newBoat.number == "" || this.newBoat.city == ""
      || this.newBoat.postalCode == "" || this.newBoat.country == ""
      || this.newBoat.boatType == "" || this.newBoat.boatLength == ""
      || this.newBoat.numberOfEngines == "" || this.newBoat.enginePower == ""
      || this.newBoat.maxSpeed == "" || this.newBoat.navigationEquipment == ""
      || this.newBoat.fishingEquipment == "" || this.newBoat.capacity == ""
      || this.newBoat.cancellationConditions == "" || this.newBoat.price == "") {
      return false;
    }

    return true;
  }

  onFocusOutBoatLength(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isBoatLengthValid = regexp.test(this.newBoat.boatLength);
  }

  onFocusOutNumberOfEngines(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isNumberOfEnginesValid = regexp.test(this.newBoat.numberOfEngines);
  }

  onFocusOutEnginePower(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isEnginePowerValid = regexp.test(this.newBoat.enginePower);
  }

  onFocusOutMaxSpeed(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isMaxSpeedValid = regexp.test(this.newBoat.maxSpeed);
  }

  onFocusOutCapacity(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isCapacityValid = regexp.test(this.newBoat.capacity);
  }

  onFocusOutPrice(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isPriceValid = regexp.test(this.newBoat.price);
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.newBoat.postalCode);
  }
}
