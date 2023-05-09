import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnovationViewComponent } from './innovation-view.component';

describe('InnovationViewComponent', () => {
  let component: InnovationViewComponent;
  let fixture: ComponentFixture<InnovationViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InnovationViewComponent]
    });
    fixture = TestBed.createComponent(InnovationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
