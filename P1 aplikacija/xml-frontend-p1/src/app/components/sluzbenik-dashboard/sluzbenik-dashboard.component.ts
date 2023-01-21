import { Component } from '@angular/core';
import { AdvancedSearchMeta } from 'src/app/model/AdvancedSearchMeta';
import { SearchResult } from 'src/app/model/PendingRequest';
import { SearchService } from 'src/app/services/search.service';
import { TokenUtilService } from 'src/app/services/token-util.service';
import { PatentService } from 'src/app/services/patent.service';

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
  razlog: string;
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

  protected vrstaPretrage: number = 1;
  basicSearchInput: string = "";
  advancedSearchInput: AdvancedSearchMeta[] = [];
  searchResults: SearchResult[];
  patentLink: string;
  currentPage: number = 0;
  otherPageName: string = "Izveštaj";

  constructor(private searchService: SearchService, private tokenUtilService: TokenUtilService, private patentService: PatentService){}
  
  ngOnInit(){
   this.getPendingRequests();
    // this.searchResults = ELEMENT_DATA;
    let role: string | null = this.tokenUtilService.getRoleFromToken();        
  
    if(role === "KORISNIK"){
      this.patentLink = "http://localhost:4200/korisnik-dashboard";
    }else{
      this.patentLink = "http://localhost:4200/sluzbenik-dashboard";
    }
  }

  tiles: Tile[] = [
    {text: 'One', cols: 3, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 1, rows: 2, color: 'lightgreen'},
    {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'Four', cols: 2, rows: 1, color: '#DDBDF1'},
  ];

  onVrstaPretrageChanged(vrstaPretrage: number){
      this.advancedSearchInput = [];
      this.basicSearchInput = "";

      if(vrstaPretrage === 1){
          this.vrstaPretrage = 1;
      }else{
          this.vrstaPretrage = 2;
      } 
  }

  basicSearch(){    
    this.searchService.basicSearch(this.basicSearchInput).subscribe({
      next: res => {
        this.searchResults = this.makeJsonListOutOfSearchResults(res);       
      },
      error: error => {
          console.error(error);
      }
    });
  }

  advancedSearch(){
      this.searchService.advancedSearch(this.advancedSearchInput).subscribe({
        next: res => {
          this.searchResults = this.makeJsonListOutOfSearchResults(res);
        },
        error: error => {
            console.error(error);
        }
      });
  }

  onDeleteAdvancedSeachInput(index: number){
    this.advancedSearchInput.splice(index, 1);
  }

  onNewUslov(){
    let newRow = {
      meta: "",
      value: "",
      operator: ""
    };
    
    this.advancedSearchInput.push(newRow);
  }

  logout(){
    localStorage.clear();
    window.location.href="http://localhost:4205/login";
  }

  changePage(){
      this.currentPage = 1 - this.currentPage;
      this.otherPageName = this.currentPage == 0 ? "Izveštaj" : "Zahtevi";
  }

  getPendingRequests(){
    this.patentService.getPendingRequests().subscribe({
      next: res => {    
          this.searchResults = this.makeJsonListOutOfSearchResults(res);
      },
      error: error => {
          console.error(error);
      }
    });
  }

  makeJsonListOutOfSearchResults(xmlString: string): any {
      let results = JSON.parse(this.tokenUtilService.xml2Json(xmlString)).searchResultsListDto.results;     
      
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
