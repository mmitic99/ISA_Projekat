import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuHomeComponent } from './su-home.component';

describe('SuHomeComponent', () => {
  let component: SuHomeComponent;
  let fixture: ComponentFixture<SuHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SuHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
