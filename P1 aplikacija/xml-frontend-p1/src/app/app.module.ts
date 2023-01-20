import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormPageComponent } from './components/form-page/form-page.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatDividerModule} from '@angular/material/divider';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatRadioModule} from '@angular/material/radio';
import { AddressComponent } from './components/address/address.component';
import { ContactComponent } from './components/contact/contact.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { XonomyFormComponent } from './components/xonomy-form/xonomy-form.component';
import { RequestListComponent } from './components/request-list/request-list.component';
import {MatTableModule} from '@angular/material/table';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { ReportPageComponent } from './components/report-page/report-page.component';
import { ToastrModule } from 'ngx-toastr';
import { DialogComponent } from './components/dialog/dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {TextFieldModule} from '@angular/cdk/text-field';
import { SluzbenikDashboardComponent } from './components/sluzbenik-dashboard/sluzbenik-dashboard.component';
import { KorisnikDashboardComponent } from './components/korisnik-dashboard/korisnik-dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    FormPageComponent,
    AddressComponent,
    ContactComponent,
    XonomyFormComponent,
    RequestListComponent,
    ReportPageComponent,
    DialogComponent,
    SluzbenikDashboardComponent,
    KorisnikDashboardComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    MatSlideToggleModule,
    MatButtonModule,
    MatInputModule,
    MatDividerModule,
    MatAutocompleteModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatRadioModule,
    MatCheckboxModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatTableModule,
    NgxExtendedPdfViewerModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatDialogModule,
    MatNativeDateModule,
    MatGridListModule,
    TextFieldModule,
    ToastrModule.forRoot({positionClass: 'toast-bottom-right', timeOut: 2000,}),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
