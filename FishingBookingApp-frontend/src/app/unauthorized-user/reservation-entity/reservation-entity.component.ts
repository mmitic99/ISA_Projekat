import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReservationEntitiesService } from '../service/reservation-entities.service';

@Component({
  selector: 'app-reservation-entity',
  templateUrl: './reservation-entity.component.html',
  styleUrls: ['./reservation-entity.component.css']
})
export class ReservationEntityComponent implements OnInit {

  private id: any;
  reservationEntity: any;
  imageObject: Array<object> = [{
    image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
    thumbImage: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
    alt: 'image not loaded',
},{
  image: 'https://upload.wikimedia.org/wikipedia/commons/c/c8/Altja_jõgi_Lahemaal.jpg',
  thumbImage: 'https://upload.wikimedia.org/wikipedia/commons/c/c8/Altja_jõgi_Lahemaal.jpg',
  alt: 'image not loaded',
},{
  image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
  thumbImage: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
  alt: 'image not loaded',
},{
  image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
  thumbImage: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
  alt: 'image not loaded',
},{
  image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
  thumbImage: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
  alt: 'image not loaded',
},{
  image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
  thumbImage: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png',
  alt: 'image not loaded',
}, {
    image: '.../iOe/xHHf4nf8AE75h3j1x64ZmZ//Z==', // Support base64 image
    thumbImage: '.../iOe/xHHf4nf8AE75h3j1x64ZmZ//Z==', // Support base64 image
    title: 'Image title', //Optional: You can use this key if want to show image with title
    alt: 'image not loaded', //Optional: You can use this key if want to show image with alt
    //order: 1 //Optional: if you pass this key then slider images will be arrange according @input: slideOrderType
}
];


  constructor(private route: ActivatedRoute, private reservationEntitiesService: ReservationEntitiesService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
    this.getEntity();;
  }

  getEntity() {
    this.reservationEntitiesService.getEntity(this.id).subscribe(
      (data)=>{
        this.reservationEntity = data
      }
    )
  }

}
