import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormPageComponent } from './components/form-page/form-page.component';
import { KorisnikDashboardComponent } from './components/korisnik-dashboard/korisnik-dashboard.component';
import { ReportPageComponent } from './components/report-page/report-page.component';
import { RequestListComponent } from './components/request-list/request-list.component';
import { SluzbenikDashboardComponent } from './components/sluzbenik-dashboard/sluzbenik-dashboard.component';
import { XonomyFormComponent } from './components/xonomy-form/xonomy-form.component';

const routes: Routes = [
  { path:"", component: FormPageComponent},
  { path:"pending-requests", component: RequestListComponent},
  { path:"xonomy", component: XonomyFormComponent},
  { path:"report-page", component: ReportPageComponent},
  { path:"sluzbenik-dashboard", component: SluzbenikDashboardComponent},
  { path:"korisnik-dashboard", component: KorisnikDashboardComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
