import { TestBed } from '@angular/core/testing';

import { SpecialReservationService } from './special-reservation.service';

describe('SpecialReservationService', () => {
  let service: SpecialReservationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpecialReservationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
