import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntityReservationComponent } from './entity-reservation.component';

describe('EntityReservationComponent', () => {
  let component: EntityReservationComponent;
  let fixture: ComponentFixture<EntityReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EntityReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EntityReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
