import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationEntityComponent } from './reservation-entity.component';

describe('ReservationEntityComponent', () => {
  let component: ReservationEntityComponent;
  let fixture: ComponentFixture<ReservationEntityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReservationEntityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationEntityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
