import { XonomyRequest } from './../../model/XonomyRequest';
import { PatentService } from './../../services/patent.service';
import { XonomyService } from './../../services/xonomy.service';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

declare const Xonomy: any;

@Component({
  selector: 'app-xonomy-form',
  templateUrl: './xonomy-form.component.html',
  styleUrls: ['./xonomy-form.component.css']
})
export class XonomyFormComponent implements OnInit{

    private editor: HTMLElement;

    constructor(private xonomyService: XonomyService, private patentService: PatentService, private toastService: ToastrService){}

    ngOnInit(): void {
    }

    ngAfterViewInit(){
      let element = document.getElementById("editor");
      
      let specification = this.xonomyService.zahtevSpecification;
      let xmlString = '<zahtev xmlns="http://www.ftn.uns.ac.rs/xwt" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> </zahtev>';
      Xonomy.render(xmlString, element, specification);
    }

    send(): void{
      let requestToSend: XonomyRequest = {
        request: Xonomy.harvest(),
      };
      this.patentService.submitRequestXonomy(requestToSend).subscribe({
        next: data => {
          console.log(data);    
          this.toastService.success("Success");          
        },
        error: error => {
          console.error(error);
          this.toastService.success("Something went wrong!");   
          }
      });
    }

}
