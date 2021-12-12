import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiHomeComponent } from './fi-home.component';

describe('SuHomeComponent', () => {
  let component: FiHomeComponent;
  let fixture: ComponentFixture<FiHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FiHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FiHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
