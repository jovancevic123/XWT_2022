import { TestBed } from '@angular/core/testing';

import { ZigService } from './zig.service';

describe('ZigService', () => {
  let service: ZigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
