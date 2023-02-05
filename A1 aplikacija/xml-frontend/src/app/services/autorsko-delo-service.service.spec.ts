import { TestBed } from '@angular/core/testing';

import { AutorskoDeloServiceService } from './autorsko-delo-service.service';

describe('AutorskoDeloServiceService', () => {
  let service: AutorskoDeloServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutorskoDeloServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
