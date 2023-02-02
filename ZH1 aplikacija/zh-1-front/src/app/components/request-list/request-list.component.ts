import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import * as saveAs from 'file-saver';
import { DialogResenjeData } from 'src/app/model/DialogResenjeData';
import { SearchResult } from 'src/app/model/SearchResult';
import { ResenjeService } from 'src/app/services/resenje.service';
import { TokenUtilService } from 'src/app/services/token-util.service';
import { ZigService } from 'src/app/services/zig.service';
import { DialogResenjeComponent } from '../dialog-resenje/dialog-resenje.component';
import { DialogComponent } from '../dialog/dialog.component';
import { ReferencesComponent } from '../references/references.component';

export interface DialogData {
  reason: string;
}

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})

export class RequestListComponent {

  @Input() requests: SearchResult[] = [];
  @Input() isUser: boolean;

  displayedColumns: string[] = [];
  reason: string;

  referencirani: SearchResult[] = [];
  referencirajuci: SearchResult[] = [];
  

  constructor(private zigService: ZigService, public dialog: MatDialog, private tokenUtilService: TokenUtilService,
              private resenjeService: ResenjeService){}

  ngOnInit(): void {
    console.log(this.requests)
    this.displayedColumns = this.isUser ? ['brojPrijaveZiga', 'nazivPodnosioca', 'html', 'pdf', 'resenje', 'reference'] :
                                     ['brojPrijaveZiga', 'nazivPodnosioca', 'html', 'pdf', 'rdf', 'json', 'odobravanje', 'odbijanje', 'resenje', 'reference'];
  }

  downloadHTML(brojPrijaveZiga: string){  
    
    this.zigService.getRequestHTML(brojPrijaveZiga).subscribe({
      next: html => {
        var file = new File([html as BlobPart], "zahtev.html", {type: "text/plain;charset=utf-8"});
        saveAs(file);          
      },
      error: error => {
        console.error(error);
        }
    });
  }

  downloadPDF(brojPrijaveZiga: string){        
    this.zigService.getRequestPDF(brojPrijaveZiga).subscribe({
      next: blob => {
        let file = new File([blob as BlobPart], "zahtev.pdf")
        saveAs(file);         
      },
      error: error => {
        console.error(error);
        }
    });
  }

  odobri(brojPrijaveZiga: string, i: number){
      this.zigService.approveRequest(brojPrijaveZiga).subscribe({
        next: data => {
          console.log(data);
          this.requests = this.requests.filter(request => request.brojPrijaveZiga !== brojPrijaveZiga)
        },
        error: error => {
          console.error(error);
          }
      });
  }

  odbij(brojPrijaveZiga: string, obrazlozenje: string, i: number){
    this.zigService.rejectRequest(brojPrijaveZiga, obrazlozenje).subscribe({
      next: data => {
        console.log(data);   
        this.requests = this.requests.filter(request => request.brojPrijaveZiga !== brojPrijaveZiga)
      },
      error: error => {
        console.error(error);
        }
    });
  }

  downloadRDF(brojPrijaveZiga: string){        
    this.zigService.getRequestRDF(brojPrijaveZiga).subscribe({
      next: blob => {
        let file = new File([blob as BlobPart], "zahtev_metadata.rdf")
        saveAs(file);         
      },
      error: error => {
        console.error(error);
        }
    });
  }

  downloadJSON(brojPrijaveZiga: string){        
    this.zigService.getRequestJSON(brojPrijaveZiga).subscribe({
      next: blob => {
        let file = new File([blob as BlobPart], "zahtev_metadata.json")
        saveAs(file);         
      },
      error: error => {
        console.error(error);
        }
    });
  }

  openDialog(brojPrijaveZiga: string, i:number): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: { reason: this.reason },
    });

    dialogRef.afterClosed().subscribe(result => {
        if(result != undefined){
            this.odbij(brojPrijaveZiga, result, i);
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

  dobaviReference(brojPrijaveZiga: string, i: number){
      this.zigService.dobaviReferencirane(brojPrijaveZiga).subscribe({
        next: res => {    
            this.referencirani = this.makeJsonListOutOfReferences(res);
            this.zigService.dobaviReferencirajuce(brojPrijaveZiga).subscribe({
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
