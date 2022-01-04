import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationEntity } from '../new-entity/ReservationEntity';
import { EntitiesService } from '../service/entities.service';
import { AvailableAppointment } from './AvailableAppointment';

@Component({
  selector: 'app-new-free-appointment',
  templateUrl: './new-free-appointment.component.html',
  styleUrls: ['./new-free-appointment.component.css']
})
export class NewAvailableAppointmentComponent implements OnInit {

  times: string[] = [];
  newAvailableAppointment = new AvailableAppointment(null, "", "", "", "");
  reservationEntity = new ReservationEntity("", "", "", "", "", "", "", "", "", "", "", "", 0, 0, "", "", "");
  isCreatingAvailableAppointment: boolean = false;
  availableAppointments: any;
  entityId: any;
  constructor(private route: ActivatedRoute, private entitiesService: EntitiesService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.reservationEntity.userId = localStorage.getItem('userId');
    this.reservationEntity.username = localStorage.getItem('mailAddress');
    this.route.params.subscribe(params => {
      this.entityId = + params['id'];
    })
    this.newAvailableAppointment.entityId = this.entityId;
    this.getEntity();
    this.getAvailableAppointments();
    this.getTimes();
  }

  createAvailableAppointment() {
    if (this.isAllValid()) {
      this.entitiesService.createAvailableAppointment(this.newAvailableAppointment).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste kerirali slobodan termin za rezervacije.")
          data.fromTime = new Date(data.fromTime[0], data.fromTime[1] - 1, data.fromTime[2], data.fromTime[3], data.fromTime[4])
          data.toTime = new Date(data.toTime[0], data.toTime[1] - 1, data.toTime[2], data.toTime[3], data.toTime[4])
          this.availableAppointments.push(data);
          this.newAvailableAppointment.startDateString = "";
          this.newAvailableAppointment.startTimeString = "";
          this.newAvailableAppointment.endDateString = "";
          this.newAvailableAppointment.endTimeString = "";
          this.isCreatingAvailableAppointment = false;
        },
        (error) => {
          this.toastr.error("Neuspešno kreiranje slobodnog termina za rezervacije.")
        }
      )
    }
    else {
      this.toastr.error("Nisu popunjena sva polja ili su neka nevalidno unesena.")
    }
  }

  isAllValid(): boolean {
    if (this.newAvailableAppointment.startDateString == "" || this.newAvailableAppointment.endDateString == ""
      || this.newAvailableAppointment.startTimeString == "" || this.newAvailableAppointment.endTimeString == "") {
      return false;
    }
    let startDateNumbers = this.newAvailableAppointment.startDateString.split("-");
    let startTimeNumbers = this.newAvailableAppointment.startTimeString.split(":");
    let endDateNumbers = this.newAvailableAppointment.endDateString.split("-");
    let endTimeNumbers = this.newAvailableAppointment.endTimeString.split(":");

    let startDate = new Date(parseInt(startDateNumbers[0]), parseInt(startDateNumbers[1]) - 1, parseInt(startDateNumbers[2]), parseInt(startTimeNumbers[0]), parseInt(startTimeNumbers[1]));
    let endDate = new Date(parseInt(endDateNumbers[0]), parseInt(endDateNumbers[1]) - 1, parseInt(endDateNumbers[2]), parseInt(endTimeNumbers[0]), parseInt(endTimeNumbers[1]));

    return startDate < endDate;
  }

  getAvailableAppointments() {
    this.entitiesService.getAvailableAppointments(this.entityId).subscribe(
      (data) => {
        this.availableAppointments = this.formatDates(data);
      }
    )
  }

  formatDates(data: any) {
    let formatedDates = []
    for (let datum of data) {
      datum.fromTime = new Date(datum.fromTime[0], datum.fromTime[1] - 1, datum.fromTime[2], datum.fromTime[3], datum.fromTime[4])
      datum.toTime = new Date(datum.toTime[0], datum.toTime[1] - 1, datum.toTime[2], datum.toTime[3], datum.toTime[4])
      formatedDates.push(datum);
    }
    return formatedDates;
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

  getTimes() {
    for (var i = 0; i < 24; i++) {
      if (i < 10) {
        this.times.push('0' + i + ':' + '00')
        this.times.push('0' + i + ':' + '30')
      }
      else {
        this.times.push(i + ':' + '00')
        this.times.push(i + ':' + '30')
      }
    }
  }

  enableCreating() {
    this.isCreatingAvailableAppointment = true;
  }

  disableCreating() {
    this.isCreatingAvailableAppointment = false;
  }

}
