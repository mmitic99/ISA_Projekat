import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurentReservationComponent } from './curent-reservation.component';

describe('CurentReservationComponent', () => {
  let component: CurentReservationComponent;
  let fixture: ComponentFixture<CurentReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CurentReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CurentReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
