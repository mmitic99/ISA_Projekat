import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatViewComponent } from './boat-view.component';

describe('BoatViewComponent', () => {
  let component: BoatViewComponent;
  let fixture: ComponentFixture<BoatViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
