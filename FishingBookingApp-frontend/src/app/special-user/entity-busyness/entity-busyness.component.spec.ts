import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntityBusynessComponent } from './entity-busyness.component';

describe('EntityBusynessComponent', () => {
  let component: EntityBusynessComponent;
  let fixture: ComponentFixture<EntityBusynessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EntityBusynessComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EntityBusynessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
