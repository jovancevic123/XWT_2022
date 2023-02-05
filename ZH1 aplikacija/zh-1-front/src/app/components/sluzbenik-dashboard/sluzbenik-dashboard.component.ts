import { Component } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
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
  role : string;

  constructor(private tokenUtilService: TokenUtilService, private zigService: ZigService, private route: ActivatedRoute, private router: RouterModule){}
  
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

  backToServicePicker(){
    window.location.href="http://localhost:4205/service-picker";
  }
}
