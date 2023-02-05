import { ReportPageComponent } from './components/report-page/report-page.component';
import { KorisnikDashboardComponent } from './components/korisnik-dashboard/korisnik-dashboard.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormPageComponent } from './components/form-page/form-page.component';
import { SearchComponent } from './components/search/search.component';

const routes: Routes = [
  { 
    path:"user/:email",
    component: KorisnikDashboardComponent,
    children:[
      {
        path:'form',
        component:FormPageComponent,
        outlet:'user-outlet'
      },
      {
        path:'search',
        component:SearchComponent,
        outlet:'user-outlet'
      },
      {
        path:'report',
        component:ReportPageComponent,
        outlet:'user-outlet'
      },
    ]
  },{ 
    path:"user",
    component: KorisnikDashboardComponent,
    children:[
      {
        path:'form',
        component:FormPageComponent,
        outlet:'user-outlet'
      },
      {
        path:'search',
        component:SearchComponent,
        outlet:'user-outlet'
      },
      {
        path:'report',
        component:ReportPageComponent,
        outlet:'user-outlet'
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
