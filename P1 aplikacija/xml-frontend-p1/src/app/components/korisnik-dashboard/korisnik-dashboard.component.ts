import { Component } from '@angular/core';
import { SearchResult } from 'src/app/model/PendingRequest';
import { TokenUtilService } from 'src/app/services/token-util.service';
import { PatentService } from 'src/app/services/patent.service';

@Component({
  selector: 'app-korisnik-dashboard',
  templateUrl: './korisnik-dashboard.component.html',
  styleUrls: ['./korisnik-dashboard.component.css']
})
export class KorisnikDashboardComponent {

  patentLink: string;
  currentPage: number = 0;
  otherPageName: string = "Xonomy forma";
  startingList: SearchResult[] = [];
  isLoading = true;

  constructor(private tokenUtilService: TokenUtilService, private patentService: PatentService){}

  ngOnInit(){
    let role: string | null = this.tokenUtilService.getRoleFromToken();        
  
    if(role === "KORISNIK"){
      this.patentLink = "http://localhost:4200/korisnik-dashboard";
    }else{
      this.patentLink = "http://localhost:4200/sluzbenik-dashboard";
    }
  }

  changePage(){
    if(this.currentPage == 2){
      this.currentPage = 0;
      this.otherPageName = "Xonomy forma";
      return;
    }
    this.currentPage = 1 - this.currentPage;
    this.otherPageName = this.currentPage == 0 ? "Xonomy forma" : "Regularna forma";
  }

  changePageTo2(){
      this.getAllMyRequests();
      this.currentPage = 2;
  }

  logout(){
    localStorage.clear();
    window.location.href="http://localhost:4205/login";
  }

  getAllMyRequests(){
      let email: string = this.tokenUtilService.getEmailFromToken() as string;
      email = "jovancevicjovan5@gmail.com";
      console.log(email);
      
      this.patentService.getUserRequests(email).subscribe({
        next: res => {    
            this.startingList = this.makeJsonListOutOfSearchResults(res);    
            this.isLoading = false;     
            console.log("JOVAN");
            
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


}
