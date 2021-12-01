import { Component, OnInit } from '@angular/core';
import { SearchFilterSortModel } from 'src/app/unauthorized-user/home/searchFilterSortModel';

@Component({
  selector: 'app-curent-reservation',
  templateUrl: './curent-reservation.component.html',
  styleUrls: ['./curent-reservation.component.css']
})
export class CurentReservationComponent implements OnInit {

  searchFilterSortModel = new SearchFilterSortModel("")

  constructor() { }

  ngOnInit(): void {
  }

}
