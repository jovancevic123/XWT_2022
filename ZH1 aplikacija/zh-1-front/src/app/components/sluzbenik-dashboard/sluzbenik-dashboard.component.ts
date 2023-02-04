import { Component } from '@angular/core';
import { SearchResult } from 'src/app/model/SearchResult';
import { Tile } from 'src/app/model/Tile';
import { TokenUtilService } from 'src/app/services/token-util.service';
import { ZigService } from 'src/app/services/zig.service';

@Component({
  selector: 'app-sluzbenik-dashboard',
  templateUrl: './sluzbenik-dashboard.component.html',
  styleUrls: ['./sluzbenik-dashboard.component.css']
})
export class SluzbenikDashboardComponent {
  zigLink: string;
  currentPage: number = 0;
  otherPageName: string = "Izveštaj";
  startingList: SearchResult[] = [];
  isLoading = true;
  email: string | null;

  constructor(private tokenUtilService: TokenUtilService, private zigService: ZigService){}
  
  ngOnInit(){
    this.email = this.tokenUtilService.getEmailFromToken();
    this.getPendingRequests();
    
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
    this.zigService.getPendingRequests().subscribe({
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

  changeToPatentServis(){
    window.location.href=`http://localhost:4200/korisnik-dashboard/${this.email}`;
  }

  changeToAutorskoDeloServis(){
    window.location.href=`http://localhost:4201/korisnik-dashboard/${this.email}`;
  }
}
