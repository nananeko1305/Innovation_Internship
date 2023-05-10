import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnovationAcceptDeclineComponent } from './innovation-accept-decline.component';

describe('InnovationAcceptDeclineComponent', () => {
  let component: InnovationAcceptDeclineComponent;
  let fixture: ComponentFixture<InnovationAcceptDeclineComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InnovationAcceptDeclineComponent]
    });
    fixture = TestBed.createComponent(InnovationAcceptDeclineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
