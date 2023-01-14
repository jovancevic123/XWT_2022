import { Component, OnInit } from '@angular/core';
import { PendingRequest } from 'src/app/model/PendingRequest';
import { PatentService } from 'src/app/services/patent.service';
import { saveAs } from 'file-saver';


const ELEMENT_DATA: PendingRequest[] = [
  { brojPrijave: "id888", nazivPodnosioca: 'Jovan', nazivPatenta: "TV" }
];

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit{

  requests: PendingRequest[] = [];
  displayedColumns: string[] = ['brojPrijave', 'nazivPodnosioca', 'nazivPatenta', 'html', 'pdf', 'odobravanje', 'odbijanje'];

  constructor(private patentService: PatentService){}

  ngOnInit(){
    this.requests = ELEMENT_DATA;
  }

  getPendingRequests(){
    this.patentService.getPendingRequests().subscribe({
      next: data => {
        console.log(data);           
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

  odobri(brojPrijave: string){
      this.patentService.approveRequest(brojPrijave).subscribe({
        next: data => {
          console.log(data);
            
        },
        error: error => {
          console.error(error);
          }
      });
  }

  odbij(brojPrijave: string){
    let obrazlozenje: string = "Obrazlozenje";

    this.patentService.rejectRequest(brojPrijave, obrazlozenje).subscribe({
      next: data => {
        console.log(data);
          
      },
      error: error => {
        console.error(error);
        }
    });
  }

}
