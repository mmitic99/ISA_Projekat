import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalServicesReservationComponent } from './additional-services-reservation.component';

describe('AdditionalServicesReservationComponent', () => {
  let component: AdditionalServicesReservationComponent;
  let fixture: ComponentFixture<AdditionalServicesReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdditionalServicesReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdditionalServicesReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
