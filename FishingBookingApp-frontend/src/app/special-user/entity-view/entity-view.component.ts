import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationEntity } from '../new-entity/ReservationEntity';
import { EntitiesService } from '../service/entities.service';

import { View, Feature, Map, Tile } from 'ol';
import VectorLayer from 'ol/layer/Vector';
import { fromLonLat, get as GetProjection, toLonLat } from 'ol/proj'
import TileLayer from 'ol/layer/Tile';
import OSM, { ATTRIBUTION } from 'ol/source/OSM';
import Point from 'ol/geom/Point';
import VectorSource from 'ol/source/Vector';

@Component({
  selector: 'app-entity-view',
  templateUrl: './entity-view.component.html',
  styleUrls: ['./entity-view.component.css']
})
export class EntityViewComponent implements OnInit {

  map: Map | undefined;
  selectedPoint: any = null;
  isNumberOfRoomsValid = true;
  isNumberOfBedsValid = true;
  isPriceValid = true;
  reservationEntity = new ReservationEntity("", "", "", "", "", "", "", "", "", "", "", "", 0, 0, "", "", "");
  isPostalCodeValid = true;
  userRole: any;
  image: any;
  private id: any;
  imageObject: Array<object> = [];
  constructor(private route: ActivatedRoute, private entitiesService: EntitiesService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    this.reservationEntity.userId = localStorage.getItem('userId');
    this.reservationEntity.username = localStorage.getItem('mailAddress');
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();
    this.getEntityImages();
  }

  getEntity() {
    this.entitiesService.getEntity(this.id).subscribe(
      (data) => {
        this.reservationEntity = new ReservationEntity(data.id, data.name, data.numberOfRooms, data.bedsPerRoom, data.price,
          data.promotionalDescription, data.rulesOfConduct, data.address.street,
          data.address.number, data.address.city, data.address.postalCode,
          data.address.country, data.address.longitude, data.address.latitude, this.reservationEntity.userId, this.reservationEntity.username, data.address.address_id);
        this.initializeMap();
      }
    )
  }

  getEntityImages() {
    this.entitiesService.getEntityImages(this.id).subscribe(
      (data) => {
        if (data.length > 0) {
          for (let base64Image of data) {
            let img = 'data:image/jpg;base64,' + base64Image;
            this.imageObject.push({ image: img, thumbImage: img });
          }
        }
      }
    )
  }

  initializeMap() {
    const features = [];
    features.push(new Feature({
      geometry: new Point(fromLonLat([this.reservationEntity.longitude, this.reservationEntity.latitude]))
    }))
    const vectorSource = new VectorSource({
      features
    });
    const vectorLayer = new VectorLayer({
      source: vectorSource
    });
    this.selectedPoint = vectorLayer;

    this.map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM({})
        }),
        vectorLayer
      ],
      view: new View({
        center: fromLonLat([this.reservationEntity.longitude, this.reservationEntity.latitude]),
        zoom: 15
      })
    });

    this.map.on('click', (evt) => {
      var coord = toLonLat(evt.coordinate);   // OVDE SE TACNO NALAZE KOORDINATE
      this.reservationEntity.longitude = coord[0];
      this.reservationEntity.latitude = coord[1];

      const features = [];
      features.push(new Feature({
        geometry: new Point(fromLonLat([this.reservationEntity.longitude, this.reservationEntity.latitude]))
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

  updateCottage() {
    if (this.isAllValid()) {
      this.entitiesService.updateCottage(this.reservationEntity).subscribe(
        (data) => {
          this.toastr.success("Uspešno ste izmenili vikendicu.", "Uspešna izmena vikendice", { timeOut: 100000 })
        },
        (error) => {
          this.toastr.error("Neuspešna izmena vikendice")
        }
      );
    }
    else {
      this.toastr.error("Nisu popunjena sva polja ili su neka nevalidno unesena.")
    }
  }

  deleteCottage() {
    if (confirm('Da li ste sigurni da želite da obrišete vikendicu?')) {
      this.entitiesService.deleteCottage(this.reservationEntity.id).subscribe(
        () => {
          this.toastr.success("Uspešno ste izbisali vikendicu.", "", { timeOut: 100000 })
        },
        () => {
          this.toastr.error("Neuspešno brisanje vikendice")
        }
      )
    }
  }

  updateBoat() {

  }

  uploadImage(ev: Event) {
    const target = ev.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];
    const size = file.size;
    if (size >= 1048576) {
      this.toastr.error("Veličina slike je prevelika, maksimalna velicina je 1MB");
      return;
    }
    this.entitiesService.uploadImage(file, this.reservationEntity.id).subscribe(
      (data) => {
        this.toastr.success("Uspešno ste postavili sliku.")
        let img = 'data:image/jpg;base64,' + data.base64Image;
        this.imageObject.unshift({ image: img, thumbImage: img })
      },
      () => {
        this.toastr.error("Neuspešno postavljanje slike")
      }
    );
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
    if (this.reservationEntity.name == "" || this.reservationEntity.promotionalDescription == ""
      || this.reservationEntity.rulesOfConduct == "" || this.reservationEntity.street == ""
      || this.reservationEntity.number == "" || this.reservationEntity.city == ""
      || this.reservationEntity.postalCode == "" || this.reservationEntity.country == "") {
      return false;
    }

    // Provera polja koja ima samo vikendica
    if (localStorage.getItem('role') == "ROLE_cottageOwner") {
      if (this.reservationEntity.numberOfRooms == "" || this.reservationEntity.bedsPerRoom == "" || this.reservationEntity.price == "") {
        return false;
      }
    }

    return true;
  }

  onFocusOutPostalCode(): void {
    var regexp = new RegExp('^[0-9]{5}$');
    this.isPostalCodeValid = regexp.test(this.reservationEntity.postalCode);
  }

  onFocusOutNumberOfRooms(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isNumberOfRoomsValid = regexp.test(this.reservationEntity.numberOfRooms);
  }

  onFocusOutNumberOfBeds(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isNumberOfBedsValid = regexp.test(this.reservationEntity.bedsPerRoom);
  }

  onFocusOutPrice(): void {
    var regexp = new RegExp('^[0-9]*$');
    this.isPriceValid = regexp.test(this.reservationEntity.price);
  }

}
