import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ReservationEntitiesService } from 'src/app/unauthorized-user/service/reservation-entities.service';
import { RequestForDeleting } from '../request-for-deleting-account/RequestForDeleting';
import { ReservationService } from '../service/reservation.service';
import { Complaint } from './Complaint';

@Component({
  selector: 'app-complaint',
  templateUrl: './complaint.component.html',
  styleUrls: ['./complaint.component.css']
})
export class ComplaintComponent implements OnInit {

  possibleEntitiesForComplaint: any;

  complaint = new Complaint("", "", "", -1)

  constructor(
    private reservationEntitiesService: ReservationEntitiesService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.getAllOldReservation();
  }

  getAllOldReservation() {
    this.reservationEntitiesService.getPossibleReservationEntitiesForComplaint().subscribe(
      (data) => {
        this.possibleEntitiesForComplaint = data;
      },
      (error) => {
        this.possibleEntitiesForComplaint = []
      }
    )
  }

  getType(type: string) {
    if (type == "cottage") {
      return "Vikendica"
    }
    else if (type == "boat") {
      return "Brod"
    }
    else if (type == "fishingInstructor") {
      return "Instruktor pecanja"
    }
    return "";
  }

  sendComplain() {
    if (this.complaint.entityIdString != "" && this.complaint.explain != "") {
      this.complaint.entityId = parseInt(this.complaint.entityIdString)
      this.reservationEntitiesService.sendComplain(this.complaint).subscribe(
        (data) => {
          this.complaint = new Complaint("", "", "", -1)
          this.toastr.success("Uspešno ste poslali žalbu")
        },
        (error) => {
          this.toastr.error(error.error)
        }
      )
    }
    else{
      this.toastr.error("Morate popuniti sva polja")
    }
  }

}
