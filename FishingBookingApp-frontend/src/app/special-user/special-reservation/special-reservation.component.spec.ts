import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialReservationComponent } from './special-reservation.component';

describe('SpecialReservationComponent', () => {
  let component: SpecialReservationComponent;
  let fixture: ComponentFixture<SpecialReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpecialReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecialReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
