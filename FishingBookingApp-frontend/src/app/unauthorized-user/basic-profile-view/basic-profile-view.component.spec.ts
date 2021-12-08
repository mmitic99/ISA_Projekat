import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BasicProfileViewComponent } from './basic-profile-view.component';

describe('BasicProfileViewComponent', () => {
  let component: BasicProfileViewComponent;
  let fixture: ComponentFixture<BasicProfileViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BasicProfileViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BasicProfileViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
