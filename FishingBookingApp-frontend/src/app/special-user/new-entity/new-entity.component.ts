import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { NewEntityService } from '../service/new-entity.service';
import { ReservationEntity } from './ReservationEntity';

@Component({
  selector: 'app-new-entity',
  templateUrl: './new-entity.component.html',
  styleUrls: ['./new-entity.component.css']
})
export class NewEntityComponent implements OnInit {

  newReservationEntity= new ReservationEntity("", "", "", "", "", "", "", "", "", "", "", "", "", "", null);
  isPostalCodeValid = true;
  userRole : any;
  constructor(private entityService: NewEntityService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    this.newReservationEntity.userId = localStorage.getItem('userId');
    this.newReservationEntity.username = localStorage.getItem('mailAddress');
  }

  createCottage(): void {
    if (this.isAllValid()) {
      this.entityService.createNewCottage(this.newReservationEntity).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste kerirali vikendicu.", "Uspešno kreiranje vikendice", { timeOut: 100000 })
          this.router.navigate(['/specialUser'])
        },
        (error) => {
          this.toastr.error("Neuspešno kreiranje vikendice")
        }
      )
    }
    else {
      this.toastr.error("Nisu popunjena sva polja.")
    }
  }

  createBoat(): void {
    // implementacija za kreiranje broda
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
    if (this.newReservationEntity.name == "" || this.newReservationEntity.promotionalDescription == ""
      || this.newReservationEntity.rulesOfConduct == "" || this.newReservationEntity.street == "" 
      || this.newReservationEntity.number == "" || this.newReservationEntity.city == ""
      || this.newReservationEntity.postalCode == "" || this.newReservationEntity.country == "") {
      return false;
    }

    // Provera polja koja ima samo vikendica
    if (localStorage.getItem('role') == "ROLE_cottageOwner"){
      if (this.newReservationEntity.numberOfRooms == "" || this.newReservationEntity.bedsPerRoom == "" || this.newReservationEntity.price == "") {
        return false;
      }
    }

    return true;
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.newReservationEntity.postalCode);
  }

}
