import { Component } from '@angular/core';
import {FormGroup, FormControl} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { PatentService } from 'src/app/services/patent.service';

@Component({
  selector: 'app-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: ['./report-page.component.css']
})
export class ReportPageComponent {

  protected pdfContent: Blob;

  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  constructor(private patentService: PatentService, private toastr: ToastrService){}

  generateReport(){
    if(!this.range.controls.start.value || !this.range.controls.end.value){
      this.toastr.error('Opseg datuma nije validan!');
      return;
    }

    this.patentService.generateReport(this.range.controls.start.value, this.range.controls.end.value).subscribe({
      next: blob => {
        this.pdfContent = blob;
      },
      error: error => {
        this.toastr.error('Došlo je do greške, pokušajte ponovo!');
        }
    });
  }

}
