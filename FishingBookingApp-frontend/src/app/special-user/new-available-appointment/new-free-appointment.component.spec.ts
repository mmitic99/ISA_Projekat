import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewAvailableAppointmentComponent } from './new-free-appointment.component';

describe('NewFreeAppointmentComponent', () => {
  let component: NewAvailableAppointmentComponent;
  let fixture: ComponentFixture<NewAvailableAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewAvailableAppointmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewAvailableAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
