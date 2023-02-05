import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogResenjeComponent } from './dialog-resenje.component';

describe('DialogResenjeComponent', () => {
  let component: DialogResenjeComponent;
  let fixture: ComponentFixture<DialogResenjeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogResenjeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogResenjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
