import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineerViewOfInnovationsComponent } from './engineer-view-of-innovations.component';

describe('EngineerViewOfInnovationsComponent', () => {
  let component: EngineerViewOfInnovationsComponent;
  let fixture: ComponentFixture<EngineerViewOfInnovationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EngineerViewOfInnovationsComponent]
    });
    fixture = TestBed.createComponent(EngineerViewOfInnovationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
