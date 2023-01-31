
import { SluzbenikDashboardComponent } from './components/sluzbenik-dashboard/sluzbenik-dashboard.component';
import { KorisnikDashboardComponent } from './components/korisnik-dashboard/korisnik-dashboard.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormPageComponent } from './components/form-page/form-page.component';

const routes: Routes = [
  { 
    path:"user", 
    component: KorisnikDashboardComponent,
    children:[
      {
        path:'form',
        component:FormPageComponent,
        outlet:'user-outlet'
      }
    ]
  },
  { 
    path:"official", 
    component: SluzbenikDashboardComponent,
    children:[]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
