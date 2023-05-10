import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnovationCreateComponent } from './innovation-create.component';

describe('InnovationCreateComponent', () => {
  let component: InnovationCreateComponent;
  let fixture: ComponentFixture<InnovationCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InnovationCreateComponent]
    });
    fixture = TestBed.createComponent(InnovationCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
