import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnovationDeclineCommentComponent } from './innovation-decline-comment.component';

describe('InnovationDeclineCommentComponent', () => {
  let component: InnovationDeclineCommentComponent;
  let fixture: ComponentFixture<InnovationDeclineCommentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InnovationDeclineCommentComponent]
    });
    fixture = TestBed.createComponent(InnovationDeclineCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
