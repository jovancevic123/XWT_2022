import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConclusionDialogComponent } from './conclusion-dialog.component';

describe('ConclusionDialogComponent', () => {
  let component: ConclusionDialogComponent;
  let fixture: ComponentFixture<ConclusionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConclusionDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConclusionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
