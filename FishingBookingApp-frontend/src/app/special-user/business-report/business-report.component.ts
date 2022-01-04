import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { EntitiesService } from '../service/entities.service';
import { TimeRange } from './TimeRange';

@Component({
  selector: 'app-business-report',
  templateUrl: './business-report.component.html',
  styleUrls: ['./business-report.component.css']
})
export class BusinessReportComponent implements OnInit {

  marks : any;
  incomes : any;
  booleanShowIncome : boolean = false;
  ownerId : any;
  reservationEntities: any;
  avgOfAllEntities: number = 0;
  sumOfIncomes: number = 0;
  ownerRole : any;
  timeRange : TimeRange = new TimeRange();
  constructor(private entitiesService : EntitiesService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.ownerId = localStorage.getItem('userId');
    this.ownerRole = localStorage.getItem('role');
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

  getAverageMarkOfAllEntities() {
    let number = 0;
    let sum = 0;
    for (let mark of this.marks) {
      if (mark.numberOfMarks > 0) {
        number = number + 1;
        sum = sum + mark.avgMark;
      }
    }
    if (number == 0) {
      this.avgOfAllEntities = -1;
    }
    else {
      this.avgOfAllEntities = sum/number;
    }
  }

  getMarks() {
    this.entitiesService.getMarksForEntitiesOfOwner(this.ownerId).subscribe(
      (data) => {
        this.marks = data
        this.getAverageMarkOfAllEntities();
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

  getIncomeById(id: any) {
    var income = this.incomes.find((m: { reservationEntitiesId: any; }) => { return m.reservationEntitiesId == id });
    return income.income;
  }

  getNumOfReservationsById(id: any) {
    var income = this.incomes.find((m: { reservationEntitiesId: any; }) => { return m.reservationEntitiesId == id });
    return income.numberOfReservations;
  }

  getSumOfIncomes() {
    let sum = 0;
    for (let income of this.incomes) {
      sum = sum + income.income;
      
    }
    this.sumOfIncomes = sum;
  }

  showIncome() {
    if (this.isDatesValid()) {
      this.entitiesService.getIncomesForEntitiesOfOwner(this.ownerId, this.timeRange).subscribe(
        (data) => {
          this.incomes = data
          this.getSumOfIncomes();
        });
      this.booleanShowIncome = true;
    } else {
      this.toastr.error("Datumi su nevalidno uneseni ili nisu uneseni svi datumi.");
    }
  }

  isDatesValid() {
    let startDateNumbers = this.timeRange.startDate.split("-");
    let startTimeNumbers = this.timeRange.startTime.split(":");
    let endDateNumbers = this.timeRange.endDate.split("-");
    let endTimeNumbers = this.timeRange.endTime.split(":");

    let startDate = new Date(parseInt(startDateNumbers[0]), parseInt(startDateNumbers[1]) - 1, parseInt(startDateNumbers[2]), parseInt(startTimeNumbers[0]), parseInt(startTimeNumbers[1]));
    let endDate = new Date(parseInt(endDateNumbers[0]), parseInt(endDateNumbers[1]) - 1, parseInt(endDateNumbers[2]), parseInt(endTimeNumbers[0]), parseInt(endTimeNumbers[1]));
  
    return startDate < endDate;
  }
}
