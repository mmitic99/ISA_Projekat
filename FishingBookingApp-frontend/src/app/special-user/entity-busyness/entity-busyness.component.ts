import { Component, OnInit } from '@angular/core';
import { CalendarOptions } from '@fullcalendar/angular';

@Component({
  selector: 'app-entity-busyness',
  templateUrl: './entity-busyness.component.html',
  styleUrls: ['./entity-busyness.component.css']
})
export class EntityBusynessComponent implements OnInit {

  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth'
  };
  role : any;
  constructor() { }

  ngOnInit(): void {
    this.role = localStorage.getItem('role');
  }

}
