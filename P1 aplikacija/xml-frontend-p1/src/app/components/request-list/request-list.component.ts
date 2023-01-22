import { DialogResenjeData } from './../sluzbenik-dashboard/sluzbenik-dashboard.component';
import { Component, OnInit, Input } from '@angular/core';
import { SearchResult } from 'src/app/model/PendingRequest';
import { PatentService } from 'src/app/services/patent.service';
import { ResenjeService } from 'src/app/services/resenje.service';
import { saveAs } from 'file-saver';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { TokenUtilService } from 'src/app/services/token-util.service';
import { DialogResenjeComponent } from '../dialog-resenje/dialog-resenje.component';
import { ReferencesComponent } from '../references/references.component';

export interface DialogData {
  reason: string;
}

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit{

  @Input() requests: SearchResult[] = [];
  @Input() isUser: boolean;

  displayedColumns: string[] = [];
  reason: string;

  referencirani: SearchResult[] = [];
  referencirajuci: SearchResult[] = [];
  

  constructor(private patentService: PatentService, public dialog: MatDialog, private tokenUtilService: TokenUtilService,
              private resenjeService: ResenjeService){}

  ngOnInit(): void {
    this.displayedColumns = this.isUser ? ['brojPrijave', 'nazivPodnosioca', 'nazivPatenta', 'html', 'pdf', 'resenje', 'reference'] :
                                     ['brojPrijave', 'nazivPodnosioca', 'nazivPatenta', 'html', 'pdf', 'rdf', 'json', 'odobravanje', 'odbijanje', 'resenje', 'reference'];
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

  openResenjeDialog(resenje: DialogResenjeData): void {
    const dialogRef = this.dialog.open(DialogResenjeComponent, {
      data: resenje,
    });
  }

  openReferenceDialog(): void {
    const dialogRef = this.dialog.open(ReferencesComponent, {
      data: {
        referencirani: this.referencirani,
        referencirajuci: this.referencirajuci
      },
    });
  }

  pogledajResenje(i: number){
    this.resenjeService.getResenje(this.requests[i].brojResenja)
    .subscribe({
      next: res => {    
          let r: DialogResenjeData = this.makeJsonResenje(res);
          this.openResenjeDialog(r);
          console.log(r);
      },
      error: error => {
          console.error(error);
      }
  })
  };

  dobaviReference(brojPrijave: string, i: number){
      this.patentService.dobaviReferencirane(brojPrijave).subscribe({
        next: res => {    
            this.referencirani = this.makeJsonListOutOfReferences(res);
            this.patentService.dobaviReferencirajuce(brojPrijave).subscribe({
              next: res => {    
                this.referencirajuci = this.makeJsonListOutOfReferences(res);
                this.openReferenceDialog();
              },
              error: error => {
                  console.error(error);
              }
            });          
        },
        error: error => {
            console.error(error);
        }
    });
  }

  makeJsonResenje(xmlString: string): DialogResenjeData{
    let results = JSON.parse(this.tokenUtilService.xml2Json(xmlString)).resenje;     
    
    let val: DialogResenjeData = {
      brojResenja: results.broj_resenja,
      imeSluzbenika: results.ime_sluzbenika,
      prezimeSluzbenika: results.prezime_sluzbenika,
      datumOdgovora: results.datum_odgovora,
      prihvacena: results.prihvacena,
      razlog: results.razlog
    }

    return val;
  }

  makeJsonListOutOfReferences(xmlString: string): any {
    let results = JSON.parse(this.tokenUtilService.xml2Json(xmlString)).searchResultsListDto.results;    
    console.log("da");
    
    if(!results){
      return [];
    }
    
    if(results.length){
      results = results;
    }
    results = [results];          

    if(results[0].length){
      for(let s of results[0]){
          if(typeof(s.brojResenja) !== 'string'){
            s.brojResenja = "";
          }
      }
      return results[0];
    }else if(typeof(results[0].brojResenja) !== 'string'){
        results[0].brojResenja = "";
    }
    
    return results;
    
}

}
