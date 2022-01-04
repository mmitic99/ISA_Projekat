import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EntitiesService } from '../service/entities.service';
import { ReservationEntity } from './ReservationEntity';

import { View, Feature, Map, Tile } from 'ol';
import VectorLayer from 'ol/layer/Vector';
import { fromLonLat, get as GetProjection, toLonLat } from 'ol/proj'
import TileLayer from 'ol/layer/Tile';
import OSM, { ATTRIBUTION } from 'ol/source/OSM';
import Point from 'ol/geom/Point';
import VectorSource from 'ol/source/Vector';

@Component({
  selector: 'app-new-entity',
  templateUrl: './new-entity.component.html',
  styleUrls: ['./new-entity.component.css']
})
export class NewEntityComponent implements OnInit {

  map: Map | undefined;
  selectedPoint: any = null;
  isNumberOfRoomsValid = true;
  isNumberOfBedsValid = true;
  isPriceValid = true;
  isPostalCodeValid = true;
  newReservationEntity = new ReservationEntity("", "", "", "", "", "", "", "", "", "", "", "", -100, -100, "", "", null);
  userRole: any;
  constructor(private entityService: EntitiesService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    this.newReservationEntity.userId = localStorage.getItem('userId');
    this.newReservationEntity.username = localStorage.getItem('mailAddress');
    this.initializeMap();
  }

  initializeMap() {
    this.map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM({})
        })
      ],
      view: new View({
        center: fromLonLat([20.36, 44.55]),
        zoom: 6.5
      })
    });

    this.map.on('click', (evt) => {
      var coord = toLonLat(evt.coordinate);   // OVDE SE TACNO NALAZE KOORDINATE
      this.newReservationEntity.longitude = coord[0];
      this.newReservationEntity.latitude = coord[1];

      const features = [];
      features.push(new Feature({
        geometry: new Point(fromLonLat([this.newReservationEntity.longitude, this.newReservationEntity.latitude]))
      }))
      const vectorSource = new VectorSource({
        features
      });
      const vectorLayer = new VectorLayer({
        source: vectorSource
      });

      if (this.selectedPoint != null) {
        this.map?.removeLayer(this.selectedPoint);
      }
      this.map?.addLayer(vectorLayer)
      this.selectedPoint = vectorLayer;
    });
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
      this.toastr.error("Nisu popunjena sva polja ili su neka nevalidno unesena.")
    }
  }

  createBoat(): void {
    // implementacija za kreiranje broda
  }

  isAllValid(): boolean {
    if (this.isAllFilled() && this.isNumberOfBedsValid && this.isNumberOfRoomsValid && this.isPriceValid && this.isPostalCodeValid) {
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
      || this.newReservationEntity.postalCode == "" || this.newReservationEntity.country == "" 
      || this.newReservationEntity.longitude == -100 || this.newReservationEntity.latitude == -100) {
      return false;
    }

    // Provera polja koja ima samo vikendica
    if (localStorage.getItem('role') == "ROLE_cottageOwner") {
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

  onFocusOutNumberOfRooms(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isNumberOfRoomsValid = regexp.test(this.newReservationEntity.numberOfRooms);
  }

  onFocusOutNumberOfBeds(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isNumberOfBedsValid = regexp.test(this.newReservationEntity.bedsPerRoom);
  }

  onFocusOutPrice(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isPriceValid = regexp.test(this.newReservationEntity.price);
  }

}
