import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnovationListComponent } from './innovation-list.component';

describe('InnovationListComponent', () => {
  let component: InnovationListComponent;
  let fixture: ComponentFixture<InnovationListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InnovationListComponent]
    });
    fixture = TestBed.createComponent(InnovationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
