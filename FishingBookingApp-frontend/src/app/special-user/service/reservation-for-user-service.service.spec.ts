import { TestBed } from '@angular/core/testing';

import { ReservationForUserServiceService } from './reservation-for-user-service.service';

describe('ReservationForUserServiceService', () => {
  let service: ReservationForUserServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservationForUserServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
