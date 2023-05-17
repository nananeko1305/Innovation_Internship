import { TestBed } from '@angular/core/testing';

import { InnovationService } from './innovation.service';

describe('InnovationService', () => {
  let service: InnovationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InnovationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
