import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormPageComponent } from './components/form-page/form-page.component';
import { RequestListComponent } from './components/request-list/request-list.component';
import { XonomyFormComponent } from './components/xonomy-form/xonomy-form.component';

const routes: Routes = [
  { path:"", component: FormPageComponent},
  { path:"pending-requests", component: RequestListComponent},
  { path:"xonomy", component: XonomyFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
