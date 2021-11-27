import { TestBed } from '@angular/core/testing';

import { ReservationEntitiesService } from './reservation-entities.service';

describe('ReservationEntitiesService', () => {
  let service: ReservationEntitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservationEntitiesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
