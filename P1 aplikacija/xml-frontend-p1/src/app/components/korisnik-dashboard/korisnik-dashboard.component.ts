import { ToastrService } from 'ngx-toastr';
import { Component } from '@angular/core';
import { SearchResult } from 'src/app/model/PendingRequest';
import { TokenUtilService } from 'src/app/services/token-util.service';
import { PatentService } from 'src/app/services/patent.service';
import { ActivatedRoute, RouterModule } from '@angular/router';

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

  email: string | null;
  role: string;

  constructor(private tokenUtilService: TokenUtilService, private patentService: PatentService, private toastService: ToastrService,  private route: ActivatedRoute, private router: RouterModule){}

  ngOnInit(){
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

  backToServicePicker(){
    window.location.href="http://localhost:4205/service-picker";
  }

  logout(){
    localStorage.clear();
    window.location.href="http://localhost:4205/login";
  }

  getAllMyRequests(){      
      this.patentService.getUserRequests(this.email as string).subscribe({
        next: res => {    
            this.startingList = this.makeJsonListOutOfSearchResults(res);    
            this.isLoading = false;                 
        },
        error: error => {
            this.toastService.warning("Something went wrong!");
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
