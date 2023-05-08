import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnovationAddComponent } from './innovation-add.component';

describe('InnovationAddComponent', () => {
  let component: InnovationAddComponent;
  let fixture: ComponentFixture<InnovationAddComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InnovationAddComponent]
    });
    fixture = TestBed.createComponent(InnovationAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
