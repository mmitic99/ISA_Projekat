import { Component, OnInit } from '@angular/core';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  searchFilterSortModel = new SearchFilterSortModel("")
  constructor() { }

  ngOnInit(): void {
  }

}
