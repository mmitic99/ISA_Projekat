import { Component, OnInit } from '@angular/core';
import { EntitiesService } from '../service/entities.service';

@Component({
  selector: 'app-business-report',
  templateUrl: './business-report.component.html',
  styleUrls: ['./business-report.component.css']
})
export class BusinessReportComponent implements OnInit {

  marks : any;
  ownerId : any;
  reservationEntities: any;
  constructor(private entitiesService : EntitiesService) { }

  ngOnInit(): void {
    this.ownerId = localStorage.getItem('userId');
    this.getAppropriateReservationEntities();
    this.getMarks();
  }

  getAppropriateReservationEntities() {
    var role = localStorage.getItem('role');
    if (role == 'ROLE_cottageOwner') {
      this.entitiesService.getAllUserCottages().subscribe(
        (data) => {
          this.reservationEntities = data;
        }
      )
    }
    else if (role == 'ROLE_boatOwner') {
      this.entitiesService.getAllUserBoats().subscribe(
        (data) => {
          this.reservationEntities = data;
        }
      )
    }
  }

  getMarks() {
    this.entitiesService.getMarksForEntitiesOfOwner(this.ownerId).subscribe(
      (data) => {
        this.marks = data
      });
  }

  getMarkById(id: any) {
    var mark = this.marks.find((m: { reservationEntitiesId: any; }) => { return m.reservationEntitiesId == id });
    return mark.avgMark;
  }

  getNumOfMarksById(id: any) {
    var mark = this.marks.find((m: { reservationEntitiesId: any; }) => { return m.reservationEntitiesId == id });
    return mark.numberOfMarks;
  }
}
