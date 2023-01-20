import { Component, OnInit, Input } from '@angular/core';
import { SearchResult } from 'src/app/model/PendingRequest';
import { PatentService } from 'src/app/services/patent.service';
import { saveAs } from 'file-saver';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { TokenUtilService } from 'src/app/services/token-util.service';

export interface DialogData {
  reason: string;
}

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent{

  @Input() requests: SearchResult[] = [];
  displayedColumns: string[] = ['brojPrijave', 'nazivPodnosioca', 'nazivPatenta', 'html', 'pdf', 'rdf', 'json', 'odobravanje', 'odbijanje'];
  reason: string;
  

  constructor(private patentService: PatentService, public dialog: MatDialog, private tokenUtilService: TokenUtilService){}

  getPendingRequests(){
    this.patentService.getPendingRequests().subscribe({
      next: data => {
        console.log(data);   
        console.log("Joca");
        
        console.log(this.tokenUtilService.xml2Json(data));

      },
      error: error => {
        console.error(error);
        }
    });
  }

  downloadHTML(brojPrijave: string){  
    
    this.patentService.getRequestHTML(brojPrijave).subscribe({
      next: html => {
        var file = new File([html as BlobPart], "zahtev.html", {type: "text/plain;charset=utf-8"});
        saveAs(file);          
      },
      error: error => {
        console.error(error);
        }
    });
  }

  downloadPDF(brojPrijave: string){        
    this.patentService.getRequestPDF(brojPrijave).subscribe({
      next: blob => {
        let file = new File([blob as BlobPart], "zahtev.pdf")
        saveAs(file);         
      },
      error: error => {
        console.error(error);
        }
    });
  }

  odobri(brojPrijave: string, i: number){
      this.patentService.approveRequest(brojPrijave).subscribe({
        next: data => {
          console.log(data);
          this.requests.splice(i, 1);
        },
        error: error => {
          console.error(error);
          }
      });
  }

  odbij(brojPrijave: string, obrazlozenje: string, i: number){
    this.patentService.rejectRequest(brojPrijave, obrazlozenje).subscribe({
      next: data => {
        console.log(data);   
        this.requests.splice(i, 1);
      },
      error: error => {
        console.error(error);
        }
    });
  }

  downloadRDF(brojPrijave: string){        
    this.patentService.getRequestRDF(brojPrijave).subscribe({
      next: blob => {
        let file = new File([blob as BlobPart], "zahtev_metadata.rdf")
        saveAs(file);         
      },
      error: error => {
        console.error(error);
        }
    });
  }

  downloadJSON(brojPrijave: string){        
    this.patentService.getRequestJSON(brojPrijave).subscribe({
      next: blob => {
        let file = new File([blob as BlobPart], "zahtev_metadata.json")
        saveAs(file);         
      },
      error: error => {
        console.error(error);
        }
    });
  }

  openDialog(brojPrijave: string, i:number): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: { reason: this.reason },
    });

    dialogRef.afterClosed().subscribe(result => {
        if(result != undefined){
            this.odbij(brojPrijave, result, i);
        }
    });
  }

}
