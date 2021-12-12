import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EntitiesService } from '../service/entities.service';
import { ReservationEntity } from './ReservationEntity';

@Component({
  selector: 'app-new-entity',
  templateUrl: './new-entity.component.html',
  styleUrls: ['./new-entity.component.css']
})
export class NewEntityComponent implements OnInit {

  newReservationEntity= new ReservationEntity("", "", "", "", "", "", "", "", "", "", "", "", null, "","","" );
  isPostalCodeValid = true;
  userRole : any;
  constructor(private entityService: EntitiesService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    this.newReservationEntity.userId = localStorage.getItem('userId');
    this.newReservationEntity.username = localStorage.getItem('mailAddress');
  }

  createClass(): void {
    if (this.isAllValid()) {
      this.entityService.createNewClass(this.newReservationEntity).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste kerirali čas.", "Uspešno kreiranje časa", { timeOut: 100000 })
          this.router.navigate(['/fishingInstructor'])
        },
        (error) => {
          this.toastr.error("Neuspešno kreiranje časa")
        }
      )
    }
    else {
      this.toastr.error("Nisu popunjena sva polja.")
    }
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



    return true;
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.newReservationEntity.postalCode);
  }

}
