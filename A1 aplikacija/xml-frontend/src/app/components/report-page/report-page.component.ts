import { AutorskoDeloServiceService } from './../../services/autorsko-delo-service.service';
import { Component } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { pdfDefaultOptions } from 'ngx-extended-pdf-viewer';

@Component({
  selector: 'app-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: ['./report-page.component.scss']
})
export class ReportPageComponent {

  protected pdfContent: Blob;

  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  constructor(private autorskoDeloService: AutorskoDeloServiceService, private toastr: ToastrService){
    pdfDefaultOptions.assetsFolder = 'bleeding-edge';
  }

  generateReport(){
    if(!this.range.controls.start.value || !this.range.controls.end.value){
      this.toastr.error('Opseg datuma nije validan!');
      return;
    }

    this.autorskoDeloService.generateReport(this.range.controls.start.value, this.range.controls.end.value).subscribe({
      next: blob => {
        console.log("Stigao");
        console.log(blob);
        this.pdfContent = blob;
      },
      error: error => {
        this.toastr.error('Došlo je do greške, pokušajte ponovo!');
        }
    });
  }
}
