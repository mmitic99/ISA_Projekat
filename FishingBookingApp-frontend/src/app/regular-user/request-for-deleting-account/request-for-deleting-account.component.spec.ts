import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestForDeletingAccountComponent } from './request-for-deleting-account.component';

describe('RequestForDeletingAccountComponent', () => {
  let component: RequestForDeletingAccountComponent;
  let fixture: ComponentFixture<RequestForDeletingAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestForDeletingAccountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestForDeletingAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
