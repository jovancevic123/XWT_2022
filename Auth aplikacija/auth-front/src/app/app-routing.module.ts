import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegistrationPageComponent } from './components/registration-page/registration-page.component';
import { ServicePickerComponent } from './components/service-picker/service-picker.component';

const routes: Routes = [
  { path: 'login', component: LoginPageComponent},
  { path: 'register', component: RegistrationPageComponent},
  { path: 'service-picker', component: ServicePickerComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
