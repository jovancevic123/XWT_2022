import { ComponentFixture, TestBed } from '@angular/core/testing';

import { XonomyFormComponent } from './xonomy-form.component';

describe('XonomyFormComponent', () => {
  let component: XonomyFormComponent;
  let fixture: ComponentFixture<XonomyFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ XonomyFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(XonomyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
