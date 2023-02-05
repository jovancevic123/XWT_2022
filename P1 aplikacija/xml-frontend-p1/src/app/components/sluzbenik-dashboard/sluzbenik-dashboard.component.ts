import { Component } from '@angular/core';
import { SearchResult } from 'src/app/model/PendingRequest';
import { TokenUtilService } from 'src/app/services/token-util.service';
import { PatentService } from 'src/app/services/patent.service';
import { ActivatedRoute, RouterModule } from '@angular/router';

export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}

export interface DialogResenjeData {
  brojResenja: string,
  imeSluzbenika: string,
  prezimeSluzbenika: string,
  datumOdgovora: string,
  prihvacena: string,
  obrazlozenje: string;
}

export interface DialogReferenceData{
  referencirani: SearchResult[],
  referencirajuci: SearchResult[]
}

// const ELEMENT_DATA: SearchResult[] = [
//   { brojPrijave: "2", nazivPodnosioca: 'Jovan', nazivPatenta: "TV" }

// ];

@Component({
  selector: 'app-sluzbenik-dashboard',
  templateUrl: './sluzbenik-dashboard.component.html',
  styleUrls: ['./sluzbenik-dashboard.component.css']
})
export class SluzbenikDashboardComponent {

  patentLink: string;
  currentPage: number = 0;
  otherPageName: string = "Izveštaj";
  startingList: SearchResult[] = [];
  isLoading = true;
  email: string | null;
  role : string;

  constructor(private tokenUtilService: TokenUtilService, private patentService: PatentService, private route: ActivatedRoute, private router: RouterModule){}
  
  ngOnInit(){
    this.getPendingRequests();

    this.email = this.route.snapshot?.paramMap?.get('email');
    console.log(this.email);
    if(this.email && this.email != 'a')
      this.tokenUtilService.setUser(this.email).subscribe({
        next: (res: any) => {
          console.log(res); 
          var parseString = require('xml2js').parseString;
          var that = this;
          parseString(res, function (err:any, result:any) {
            console.dir(result);
            let token = {
                  firstname: result.User.firstname[0],
                  lastname: result.User.lastname[0],
                  email: result.User.email[0],
                  password: result.User.password[0],
                  role: result.User.role[0],
            };
            localStorage.setItem("user", JSON.stringify(token));
            that.role = token.role;
          });
        },
        error: (error: any) => {
            console.error(error);
        }
      });      
    
    let role: string | null = this.tokenUtilService.getRoleFromToken();        
  }

  tiles: Tile[] = [
    {text: 'One', cols: 3, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 1, rows: 2, color: 'lightgreen'},
    {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'Four', cols: 2, rows: 1, color: '#DDBDF1'},
  ];

  logout(){
    localStorage.clear();
    window.location.href="http://localhost:4205/login";
  }

  getPendingRequests(){
    this.patentService.getPendingRequests().subscribe({
      next: res => {    
          this.startingList = this.makeJsonListOutOfSearchResults(res);    
          this.isLoading = false;     
      },
      error: error => {
          console.error(error);
      }
    });
  }

  makeJsonListOutOfSearchResults(xmlString: string): any {
    let results = JSON.parse(this.tokenUtilService.xml2Json(xmlString)).searchResultsListDto.results;     
    
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

  changePage(){
      this.currentPage = 1 - this.currentPage;
      this.otherPageName = this.currentPage == 0 ? "Izveštaj" : "Zahtevi";
  }

  backToServicePicker(){
    window.location.href="http://localhost:4205/service-picker";
  }
}
