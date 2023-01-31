import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KorisnikDashboardComponent } from './korisnik-dashboard.component';

describe('KorisnikDashboardComponent', () => {
  let component: KorisnikDashboardComponent;
  let fixture: ComponentFixture<KorisnikDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KorisnikDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KorisnikDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
