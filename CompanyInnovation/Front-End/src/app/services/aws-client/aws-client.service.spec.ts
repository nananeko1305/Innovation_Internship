import { TestBed } from '@angular/core/testing';

import { AwsClientService } from './aws-client.service';

describe('AwsClientService', () => {
  let service: AwsClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AwsClientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
