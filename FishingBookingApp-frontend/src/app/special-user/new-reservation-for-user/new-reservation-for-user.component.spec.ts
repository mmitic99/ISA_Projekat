import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewReservationForUserComponent } from './new-reservation-for-user.component';

describe('NewReservationForUserComponent', () => {
  let component: NewReservationForUserComponent;
  let fixture: ComponentFixture<NewReservationForUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewReservationForUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewReservationForUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
